package dev.vk.jfc.app.storage.appstorage.services;

import dev.vk.jfc.app.storage.appstorage.entities.HeadersEntity;
import dev.vk.jfc.app.storage.appstorage.entities.ImageEntity;
import dev.vk.jfc.app.storage.appstorage.repository.ImageRepository;
import dev.vk.jfc.jfccommon.Jfc;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@Primary
@AllArgsConstructor
public class ImageDataStorageService02 implements ImageDataStorageService {

    private final static Logger logger = LoggerFactory.getLogger(ImageDataStorageService02.class);

    private final ImageRepository imageRepository;

    private void fillInHeaderData(HeadersEntity entity, Map<String, Object> headers) {
        entity.setId(UUID.fromString(String.valueOf(headers.get(Jfc.K_UUID))));

        entity.setBrokerTimestamp(Long.valueOf(String.valueOf(headers.get(Jfc.K_BROKER_TIMESTAMP))));
        entity.setTimestamp(Long.valueOf(String.valueOf(headers.get(Jfc.K_TIMESTAMP))));

        entity.setFrameNo((Integer) headers.get(Jfc.K_FRAMENO));
        entity.setLocalID((Integer) headers.get(Jfc.K_LOCALID));

        entity.setHostname(String.valueOf(headers.get(Jfc.K_HOSTNAME)));
        entity.setS3Path(String.valueOf(headers.get(Jfc.K_S3PATH)));
        entity.setSource(String.valueOf(headers.get(Jfc.K_SOURCE)));
        entity.setMessageType(String.valueOf(headers.get(Jfc.K_MESSAGE_TYPE)));
    }


    @Override
    public void onFrameImage(Map<String, Object> headers, byte[] payload) {
        logger.info("onFrameImage");

        UUID uuid = UUID.fromString(String.valueOf(headers.get(Jfc.K_UUID)));
        ImageEntity imageEntity = imageRepository.findById(uuid).orElseGet(ImageEntity::new);
        fillInHeaderData(imageEntity, headers);
        imageEntity = imageRepository.save(imageEntity);

    }

    @Override
    public void onFrameFacesImage(Map<String, Object> headers, byte[] payload) {
        logger.info("onFrameFacesImage");
    }

    @Override
    public void onIndexedDataMessage(Map<String, Object> headers, byte[] body) {
        logger.info("onIndexedDataMessage");
    }
}
