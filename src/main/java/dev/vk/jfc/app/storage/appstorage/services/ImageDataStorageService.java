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

    @Transactional
    protected ImageEntity getImageEntity(UUID uuid, UUID indexedDataId) {
        ImageEntity pImg = imageRepository.findById(uuid).orElseGet(ImageEntity::new);
        pImg.setId(uuid);
        if (pImg.getElements() == null) {
            pImg.setElements(new ArrayList<>());
        }
        if (pImg.getIndexedDataEntity() == null) {
//            IndexedDataEntity indexedDataEntity = new IndexedDataEntity();
            if (null == indexedDataId) indexedDataId = UUID.randomUUID();
//            indexedDataEntity.setId(indexedDataId);
//            pImg.setIndexedDataEntity(indexedDataEntity);
            pImg.setIndexedDataEntity(getIndexedDataEntity(indexedDataId));
        }
        return pImg;
    }

    @Transactional
    protected IndexedDataEntity getIndexedDataEntity(UUID uuid) {
        Optional<IndexedDataEntity> lookForEntity = indexedDataRepository.findById(uuid);
        IndexedDataEntity entity = lookForEntity.orElseGet(IndexedDataEntity::new);
        entity.setId(uuid);
        if (entity.getElements() == null) {
            entity.setElements(new ArrayList<>());
        }
        return indexedDataRepository.save(entity);
    }

    public void onImageMessage(Message message) {
        Map<String, Object> headers = message.getMessageProperties().getHeaders();
        getImageEntity(headers);
    }

    @Transactional
    public ImageEntity getImageEntity(Map<String, Object> headers) {
        UUID uuid = UUID.fromString(String.valueOf(headers.get(Jfc.K_UUID)));
        ImageEntity entity = getImageEntity(uuid, null);
        fillInHeaderData(entity, headers);
        return imageRepository.save(entity);
    }

    @Transactional
    public ImageEntity getImageFaceEntity(Map<String, Object> headers) {
        UUID parentUuid = UUID.fromString(String.valueOf(headers.get(Jfc.K_PARENT_UUID)));
        ImageEntity me = getImageEntity(headers);
        fillInHeaderData(me, headers);
        ImageEntity parentImage = getImageEntity(parentUuid, null);

        me.setContainer(parentImage);
        parentImage.getElements().add(me);
        imageRepository.save(parentImage);

        return me;
    }

    @Transactional
    public IndexedDataEntity getIndexedDataEntity(Map<String, Object> headers, byte[] payload) {
        UUID uuid = UUID.fromString(String.valueOf(headers.get(Jfc.K_UUID)));
        UUID parentUuid = UUID.fromString((String.valueOf(headers.get(Jfc.K_PARENT_UUID))));

        IndexedDataEntity me = getIndexedDataEntity(uuid);
        fillInHeaderData(me, headers);
//        me = indexedDataRepository.save(me);

        ImageEntity parentImage = getImageEntity(parentUuid, uuid);
        me.setImageEntity(parentImage);
        me = indexedDataRepository.save(me);

        return me;
    }


    @Transactional
    public void onFrameFacesImage(Message message) {
        Map<String, Object> headers = message.getMessageProperties().getHeaders();
        ImageEntity me = getImageFaceEntity(headers);
        imageRepository.save(me);
    }

    @Transactional
    @SneakyThrows
    public IndexedDataEntity onIndexedData(Map<String, Object> headers, byte[] bBody) {
        UUID uuid = UUID.fromString(String.valueOf(headers.get(Jfc.K_UUID)));
        UUID parentUuid = UUID.fromString(String.valueOf(headers.get(Jfc.K_PARENT_UUID)));
        ImageEntity imageEntity = getImageEntity(parentUuid, null);

//        IndexedDataEntity indexedDataEntity = getIndexedDataEntity(uuid);
//        fillInHeaderData(indexedDataEntity, headers);
//        indexedDataEntity.setImageEntity(imageEntity);

        String strBody = new String(bBody);
        logger.debug("Processing {}", strBody);

        List<ImageDataItemDto> theList = jsonObjectMapper.readValue(strBody,
                new TypeReference<List<ImageDataItemDto>>() {
                }
        );

        /*
        for (ImageDataItemDto dto : theList) {
            logger.debug("Element: {} ", dto);

            ImageDataItemEntity entity = modelMapper.map(dto, ImageDataItemEntity.class);
            entity.setId(UUID.randomUUID());
            entity.setContainer(indexedDataEntity);
            entity = imageDataItemRepository.save(entity);
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
         */

//        indexedDataRepository.save(indexedDataEntity);
        logger.info("Finished...");
//        return indexedDataEntity;
        return null;
    }

    @Transactional
    @SneakyThrows
    public void onIndexedDataMessage(Message message) {

        Map<String, Object> headers = message.getMessageProperties().getHeaders();
        byte[] bBody = message.getBody();
        onIndexedData(headers, bBody);
    }
}
