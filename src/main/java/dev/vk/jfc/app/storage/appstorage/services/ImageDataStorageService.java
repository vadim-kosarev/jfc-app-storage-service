package dev.vk.jfc.app.storage.appstorage.services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.vk.jfc.app.storage.appstorage.entities.*;
import dev.vk.jfc.app.storage.appstorage.repository.ImageEntityRepository;
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
    private final ImageEntityRepository imageEntityRepository;
    private final IndexedDataRepository indexedDataRepository;

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
    protected ProcessedImage getRootProcessedImage(UUID uuid) {
        Optional<ProcessedImage> lookForEntity = processedImageRepository.findById(uuid);
        ProcessedImage pImg = lookForEntity.orElseGet(ProcessedImage::new);
        pImg.setId(uuid);
        if (pImg.getElements() == null) {
            pImg.setElements(new ArrayList<>());
        }
        return processedImageRepository.save(pImg);
    }

    protected IndexedData getIndexedData(UUID uuid) {
        IndexedData entity = indexedDataRepository.findById(uuid).orElseGet(IndexedData::new);
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
        ProcessedImage processedImage = getRootProcessedImage(uuid);
        fillInHeaderData(processedImage, headers);
        processedImageRepository.save(processedImage);
    }

    @Transactional
    public void onFrameFacesImage(Message message) {
        Map<String, Object> headers = message.getMessageProperties().getHeaders();
        UUID uuid = UUID.fromString(String.valueOf(headers.get(Jfc.K_UUID)));
        UUID parentUuid = UUID.fromString(String.valueOf(headers.get(Jfc.K_PARENT_UUID)));
        ProcessedImage processedImage = getRootProcessedImage(parentUuid);

        tmp_ImageEntity imageEntity = imageEntityRepository.findById(uuid).orElseGet(tmp_ImageEntity::new);
        fillInHeaderData(imageEntity, headers);
        imageEntity = imageEntityRepository.save(imageEntity);

        processedImage.getElements().add(imageEntity);
        processedImageRepository.save(processedImage);

    }

    @Transactional
    @SneakyThrows
    public void onIndexedData(Message message) {
        Map<String, Object> headers = message.getMessageProperties().getHeaders();
        UUID uuid = UUID.fromString(String.valueOf(headers.get(Jfc.K_UUID)));
        UUID parentUuid = UUID.fromString(String.valueOf(headers.get(Jfc.K_PARENT_UUID)));
        ProcessedImage processedImage = getRootProcessedImage(parentUuid);

        IndexedData indexedData = processedImage.getIndexedData();
        if (null == indexedData) {
            indexedData = getIndexedData(uuid);
            indexedData.setLabel("**Label: `IndexedData` / %s".formatted(uuid));
            indexedData.setContainer(processedImage);
            processedImage.setIndexedData(indexedData);
        }
        processedImageRepository.save(processedImage);

        byte[] bBody = message.getBody();
        String strBody = new String(bBody);
        logger.debug("Processing {}", strBody);

    }
}
