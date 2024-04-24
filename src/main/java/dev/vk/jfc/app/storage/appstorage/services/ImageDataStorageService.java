package dev.vk.jfc.app.storage.appstorage.services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.vk.jfc.app.storage.appstorage.entities.*;
import dev.vk.jfc.app.storage.appstorage.repository.ImageDataRepository;
import dev.vk.jfc.app.storage.appstorage.repository.IndexedDataRepository;
import dev.vk.jfc.app.storage.appstorage.repository.ProcessedImageRepository;
import dev.vk.jfc.jfccommon.Jfc;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class ImageDataStorageService {

    private static final Logger logger = LoggerFactory.getLogger(ImageDataStorageService.class);

    private final ProcessedImageRepository processedImageRepository;
    private final IndexedDataRepository indexedDataRepository;
    private final ImageDataRepository imageDataRepository;

    private final static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

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
    protected ImageEntity getRootProcessedImage(UUID uuid) {
        Optional<ImageEntity> lookForEntity = processedImageRepository.findById(uuid);
        ImageEntity pImg = lookForEntity.orElseGet(ImageEntity::new);
        pImg.setId(uuid);
        if (pImg.getElements() == null) {
            pImg.setElements(new ArrayList<>());
        }
        return processedImageRepository.save(pImg);
    }

    protected IndexedDataEntity getIndexedData(UUID uuid) {
        IndexedDataEntity entity = indexedDataRepository.findById(uuid).orElseGet(IndexedDataEntity::new);
        entity.setId(uuid);
        if (entity.getElements() == null) {
            entity.setElements(new ArrayList<>());
        }
        return indexedDataRepository.save(entity);
    }

    @Transactional
    public void onProcessedImage(Message message) {
        Map<String, Object> headers = message.getMessageProperties().getHeaders();
        UUID uuid = UUID.fromString(String.valueOf(headers.get(Jfc.K_UUID)));
        ImageEntity imageEntity = getRootProcessedImage(uuid);
        fillInHeaderData(imageEntity, headers);
        processedImageRepository.save(imageEntity);
    }

    @Transactional
    public void onFrameFacesImage(Message message) {
        Map<String, Object> headers = message.getMessageProperties().getHeaders();
        UUID uuid = UUID.fromString(String.valueOf(headers.get(Jfc.K_UUID)));
        UUID parentUuid = UUID.fromString(String.valueOf(headers.get(Jfc.K_PARENT_UUID)));
        ImageEntity processedImageEntity = getRootProcessedImage(parentUuid);

        ImageDataEntity imageEntity = imageDataRepository.findById(uuid).orElseGet(ImageDataEntity::new);
        fillInHeaderData(imageEntity, headers);
        imageEntity = imageDataRepository.save(imageEntity);

        processedImageEntity.getElements().add(imageEntity);
        processedImageRepository.save(processedImageEntity);
    }

    @Transactional
    @SneakyThrows
    public void onIndexedData(Message message) {
        Map<String, Object> headers = message.getMessageProperties().getHeaders();
        UUID uuid = UUID.fromString(String.valueOf(headers.get(Jfc.K_UUID)));
        UUID parentUuid = UUID.fromString(String.valueOf(headers.get(Jfc.K_PARENT_UUID)));
        ImageEntity imageEntity = getRootProcessedImage(parentUuid);

        IndexedDataEntity indexedDataEntity = imageEntity.getIndexedDataEntity();
        if (null == indexedDataEntity) {
            indexedDataEntity = getIndexedData(uuid);
            indexedDataEntity.setLabel("**Label: `IndexedDataEntity` / %s".formatted(uuid));
            indexedDataEntity.setContainer(imageEntity);
            imageEntity.setIndexedDataEntity(indexedDataEntity);
        }
        processedImageRepository.save(imageEntity);

        byte[] bBody = message.getBody();
        String strBody = new String(bBody);
        logger.debug("Processing {}", strBody);

    }
}
