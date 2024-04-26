package dev.vk.jfc.app.storage.appstorage.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.vk.jfc.app.storage.appstorage.dto.ImageDataItemDto;
import dev.vk.jfc.app.storage.appstorage.entities.*;
import dev.vk.jfc.app.storage.appstorage.entities.data.ArrayItemId;
import dev.vk.jfc.app.storage.appstorage.repository.ImageDataItemRepository;
import dev.vk.jfc.app.storage.appstorage.repository.ImageDataRepository;
import dev.vk.jfc.app.storage.appstorage.repository.IndexedDataRepository;
import dev.vk.jfc.app.storage.appstorage.repository.ImageRepository;
import dev.vk.jfc.jfccommon.Jfc;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.*;
import java.util.List;

@Service
@AllArgsConstructor
public class ImageDataStorageService {

    private static final Logger logger = LoggerFactory.getLogger(ImageDataStorageService.class);

    private final ObjectMapper jsonObjectMapper;

    private final ImageRepository imageRepository;
    private final IndexedDataRepository indexedDataRepository;
    private final ImageDataRepository imageDataRepository;
    private final ModelMapper modelMapper;
    private final ImageDataItemRepository imageDataItemRepository;

    protected void fillInHeaderData(HeadersEntity image, Map<String, Object> headers) {
        image.setId(UUID.fromString(String.valueOf(headers.get(Jfc.K_UUID))));

        image.setBrokerTimestamp(Long.valueOf(String.valueOf(headers.get(Jfc.K_BROKER_TIMESTAMP))));
        image.setTimestamp(Long.valueOf(String.valueOf(headers.get(Jfc.K_TIMESTAMP))));

        image.setFrameNo((Integer) headers.get(Jfc.K_FRAMENO));
        image.setLocalID((Integer) headers.get(Jfc.K_LOCALID));

        image.setHostname(String.valueOf(headers.get(Jfc.K_HOSTNAME)));
        image.setS3Path(String.valueOf(headers.get(Jfc.K_S3PATH)));
        image.setSource(String.valueOf(headers.get(Jfc.K_SOURCE)));
        image.setMessageType(String.valueOf(headers.get(Jfc.K_MESSAGE_TYPE)));
    }

    private ImageEntity getImageEntity(UUID uuid) {
        Optional<ImageEntity> lookForEntity = imageRepository.findById(uuid);
        ImageEntity pImg = lookForEntity.orElseGet(ImageEntity::new);
        pImg.setId(uuid);
        if (pImg.getElements() == null) {
            pImg.setElements(new ArrayList<>());
        }
        return pImg;
    }

    public void onImageMessage(Message message) {
        Map<String, Object> headers = message.getMessageProperties().getHeaders();
        getImageEntity(headers);
    }

    @Transactional
    public ImageEntity getImageEntity(Map<String, Object> headers) {
        UUID uuid = UUID.fromString(String.valueOf(headers.get(Jfc.K_UUID)));
        ImageEntity entity = getImageEntity(uuid);
        fillInHeaderData(entity, headers);
        return imageRepository.save(entity);
    }

    @Transactional
    public ImageEntity getImageFaceEntity(Map<String, Object> headers) {
        UUID parentUuid = UUID.fromString(String.valueOf(headers.get(Jfc.K_PARENT_UUID)));
        ImageEntity me = getImageEntity(headers);
        fillInHeaderData(me, headers);
        ImageEntity parentImage = getImageEntity(parentUuid);

        me.setContainer(parentImage);
        parentImage.getElements().add(me);
        imageRepository.save(parentImage);

//        me.setContainer(parentImage);
//        me = imageRepository.save(me);
        return me;
    }

    @Transactional
    public void onFrameFacesImage(Message message) {
        Map<String, Object> headers = message.getMessageProperties().getHeaders();
        ImageEntity me = getImageFaceEntity(headers);
        imageRepository.save(me);
    }

    @Transactional
    protected IndexedDataEntity getIndexedData(UUID uuid) {
        IndexedDataEntity entity = indexedDataRepository.findById(uuid).orElseGet(IndexedDataEntity::new);
        entity.setId(uuid);
        if (entity.getElements() == null) {
            entity.setElements(new ArrayList<>());
        }
        return indexedDataRepository.save(entity);
    }

    @Transactional
    @SneakyThrows
    public void onIndexedData(Message message) {

        Map<String, Object> headers = message.getMessageProperties().getHeaders();
        UUID uuid = UUID.fromString(String.valueOf(headers.get(Jfc.K_UUID)));
        UUID parentUuid = UUID.fromString(String.valueOf(headers.get(Jfc.K_PARENT_UUID)));
        ImageEntity imageEntity = getImageEntity(parentUuid);

        IndexedDataEntity indexedDataEntity = imageEntity.getIndexedDataEntity();
        if (null == indexedDataEntity) {
            indexedDataEntity = getIndexedData(uuid);
            indexedDataEntity.setLabel("**Label: `IndexedDataEntity` / %s".formatted(uuid));
            indexedDataEntity.setContainer(imageEntity);
            imageEntity.setIndexedDataEntity(indexedDataEntity);
        }
        indexedDataRepository.save(indexedDataEntity);

        byte[] bBody = message.getBody();
        String strBody = new String(bBody);
        logger.debug("Processing {}", strBody);

        List<ImageDataItemDto> theList = jsonObjectMapper.readValue(strBody,
                new TypeReference<List<ImageDataItemDto>>() {
                }
        );

        for (ImageDataItemDto dto : theList) {
            logger.debug("Element: {} ", dto);

            ImageDataItemEntity entity = modelMapper.map(dto, ImageDataItemEntity.class);
            logger.debug(" --> Mapped to: {}", entity);

            if (entity.getId() == null) {
                entity.setId(UUID.randomUUID());
            }
            entity.setParentImageData(indexedDataEntity);

            // ========================================================
            for (int i = 0; i < dto.getFaceVector().length; i++) {
                entity.getFaceVector().add(
                        new FloatArrayItemEntity(
                                new ArrayItemId(entity.getId(), i), dto.getFaceVector()[i]
                        )
                );
            }

            // ========================================================

            imageDataItemRepository.save(entity);

        }

//        indexedDataRepository.save(indexedDataEntity);
    }
}
