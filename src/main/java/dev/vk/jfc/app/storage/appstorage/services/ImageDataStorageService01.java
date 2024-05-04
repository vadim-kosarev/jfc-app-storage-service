package dev.vk.jfc.app.storage.appstorage.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.vk.jfc.app.storage.appstorage.dto.ImageDataItemDto;
import dev.vk.jfc.app.storage.appstorage.entities.*;
import dev.vk.jfc.app.storage.appstorage.entities.data.ArrayItemId;
import dev.vk.jfc.app.storage.appstorage.repository.ImageDataItemRepository;
import dev.vk.jfc.app.storage.appstorage.repository.ImageRepository;
import dev.vk.jfc.app.storage.appstorage.repository.IndexedDataRepository;
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

@Service
@AllArgsConstructor
public class ImageDataStorageService01 implements ImageDataStorageService {

    private static final Logger logger = LoggerFactory.getLogger(ImageDataStorageService01.class);

    private final ObjectMapper jsonObjectMapper;

    private final ImageRepository imageRepository;
    private final IndexedDataRepository indexedDataRepository;
//    private final ImageDataRepository imageDataRepository;
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

    @SneakyThrows
    protected ImageEntity onImageMessage(UUID uuid, boolean toSave) {
        Optional<ImageEntity> entityOpt = imageRepository.findById(uuid);
        if (entityOpt.isPresent()) {
            return entityOpt.get();
        } else {
            ImageEntity entity = new ImageEntity(uuid);
            if (entity.getElements() == null) {
                entity.setElements(new ArrayList<>());
            }
            if (toSave) {
                logger.info("### SAVING: {} {}", entity.getClass().getSimpleName(), jsonObjectMapper.writeValueAsString(entity));
                entity = imageRepository.save(entity);
            }
            return entity;
        }
    }

    @SneakyThrows
    protected IndexedDataEntity onIndexedDataEntity(UUID uuid) {
        Optional<IndexedDataEntity> lookForEntity = indexedDataRepository.findById(uuid);
        if (lookForEntity.isPresent()) {
            return lookForEntity.get();
        } else {
            IndexedDataEntity entity = new IndexedDataEntity();
            entity.setId(uuid);
            if (entity.getElements() == null) {
                entity.setElements(new ArrayList<>());
            }
            logger.info("### SAVING: {} {}", entity.getClass().getSimpleName(), jsonObjectMapper.writeValueAsString(entity));
            return indexedDataRepository.save(entity);
        }
    }

    @SneakyThrows
    public ImageEntity onImageMessage(Message message) {
        Map<String, Object> headers = message.getMessageProperties().getHeaders();
        ImageEntity entity = onImageMessage(headers, false);
        logger.info("### SAVING: {} {}", entity.getClass().getSimpleName(), jsonObjectMapper.writeValueAsString(entity));
        entity = imageRepository.save(entity);
        return entity;

    }

    @SneakyThrows
    private void dumpEntity(ImageEntity entity) {
        String var = jsonObjectMapper.writeValueAsString(entity);
        //logger.info(" === DUMP === {}\n{}", entity.getId(), var);
    }

    @SneakyThrows
    public ImageEntity onImageMessage(Map<String, Object> headers, boolean toSave) {
        UUID uuid = UUID.fromString(String.valueOf(headers.get(Jfc.K_UUID)));
        ImageEntity entity = onImageMessage(uuid, false);
        fillInHeaderData(entity, headers);
        if (toSave) {
            logger.info("### SAVING: {} {}", entity.getClass().getSimpleName(), jsonObjectMapper.writeValueAsString(entity));
            entity = imageRepository.save(entity);
        }
        dumpEntity(entity);
        return entity;
    }

    public ImageEntity getImageFaceEntity(Map<String, Object> headers, boolean toSave) {
        UUID parentUuid = UUID.fromString(String.valueOf(headers.get(Jfc.K_PARENT_UUID)));
        ImageEntity me = onImageMessage(headers, false);
        fillInHeaderData(me, headers);
        ImageEntity parentImage = onImageMessage(parentUuid, false);

        me.setContainer(parentImage);
        parentImage.getElements().add(me);

        return me;
    }

    @SneakyThrows
    public IndexedDataEntity getIndexedDataEntity(Map<String, Object> headers, byte[] payload) {
        UUID uuid = UUID.fromString(String.valueOf(headers.get(Jfc.K_UUID)));
        UUID parentUuid = UUID.fromString((String.valueOf(headers.get(Jfc.K_PARENT_UUID))));

        IndexedDataEntity me = onIndexedDataEntity(uuid);

        ImageEntity parentImage = onImageMessage(parentUuid, false);
        parentImage.setIndexedDataEntity(me);

        String strBody = new String(payload);
        List<ImageDataItemDto> theList = jsonObjectMapper.readValue(strBody,
                new TypeReference<List<ImageDataItemDto>>() {
                }
        );

        for (ImageDataItemDto item : theList) {
            logger.info("Read item: {}", item);

            IndexedDataItemEntity itemEntity = new IndexedDataItemEntity();
            itemEntity.setId(UUID.randomUUID());
            itemEntity.setParentImageData(me);
            modelMapper.map(item, itemEntity);

            int cnt = 0;
            itemEntity.setFaceVector(new ArrayList<>());
            for (float fData : item.getFaceVector()) {
                itemEntity.getFaceVector().add(
                        new FloatArrayItemEntity(new ArrayItemId(
                                itemEntity.getId(), cnt++
                        ), fData)
                );
            }

            logger.info("itemEntity: {}", itemEntity);

            me.getElements().add(itemEntity);
        }

        fillInHeaderData(me, headers);
        me.setImageEntity(parentImage);

        return me;
    }

    @Transactional
    @SneakyThrows
    public ImageEntity onFrameFacesImage(Message message) {
        Map<String, Object> headers = message.getMessageProperties().getHeaders();
        ImageEntity entity = getImageFaceEntity(headers, false);
        logger.info(":::{} {}", entity.getClass().getSimpleName(), jsonObjectMapper.writeValueAsString(entity));
        return entity;
    }

    @Transactional
    @SneakyThrows
    public void onIndexedDataMessage(Map<String, Object> headers, byte[] payload) {
        IndexedDataEntity entity = getIndexedDataEntity(headers, payload);
        logger.info("### SAVING: {} {}", entity.getClass().getSimpleName(), jsonObjectMapper.writeValueAsString(entity));
        indexedDataRepository.save(entity);
    }

    @Override
    @SneakyThrows
    public void onFrameImage(Map<String, Object> headers, byte[] payload) {
        ImageEntity entity = onImageMessage(headers, false);
        logger.info("### SAVING: {} {}", entity.getClass().getSimpleName(), jsonObjectMapper.writeValueAsString(entity));
        imageRepository.save(entity);
    }

    @Override
    @SneakyThrows
    public void onFrameFacesImage(Map<String, Object> headers, byte[] payload) {
        ImageEntity entity = getImageFaceEntity(headers, false);
        logger.info("### SAVING: {} {}", entity.getClass().getSimpleName(), jsonObjectMapper.writeValueAsString(entity));
        imageRepository.save(entity);
    }
}
