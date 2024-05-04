package dev.vk.jfc.app.storage.appstorage.services;

import dev.vk.jfc.app.storage.appstorage.entities.HeadersEntity;
import dev.vk.jfc.app.storage.appstorage.entities.ImageEntity;
import dev.vk.jfc.app.storage.appstorage.repository.ImageRepository;
import dev.vk.jfc.jfccommon.Jfc;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

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
        ImageEntity imageEntity = getImageEntity(headers, uuid);
        fillInHeaderData(imageEntity, headers);
        imageEntity = imageRepository.save(imageEntity);

    }

    private @NotNull ImageEntity getImageEntity(Map<String, Object> headers, UUID uuid) {
        ImageEntity imageEntity = imageRepository.findById(uuid).orElseGet(ImageEntity::new);
        if (imageEntity.getId() == null) {
            imageEntity.setId(uuid);
            imageEntity.setLabel("Created as new: %s".formatted(uuid));
        } else {
            imageEntity.setLabel("Found from DB: %s".formatted(uuid));
        }
        if (imageEntity.getElements() == null) {
            imageEntity.setElements(new ArrayList<>());
        }
        return imageEntity;
    }

    @Override
    public void onFrameFacesImage(Map<String, Object> headers, byte[] payload) {

        UUID uuid = UUID.fromString(String.valueOf(headers.get(Jfc.K_UUID)));
        ImageEntity imageEntity = getImageEntity(headers, uuid);
        fillInHeaderData(imageEntity, headers);

        UUID parentUuid = UUID.fromString(String.valueOf(headers.get(Jfc.K_PARENT_UUID)));
        ImageEntity parentImageEntity = getImageEntity(headers, parentUuid);

        boolean contains = parentImageEntity.getElements()
                .stream().map(item -> item.getId())
                .collect(Collectors.toUnmodifiableSet())
                .contains(uuid);

        if (!contains) {
            parentImageEntity.getElements().add(imageEntity);
            parentImageEntity = imageRepository.save(parentImageEntity);
        }

        imageEntity.setContainer(parentImageEntity);
        imageEntity = imageRepository.save(imageEntity);

        logger.info("onFrameFacesImage");
    }

    @Override
    public void onIndexedDataMessage(Map<String, Object> headers, byte[] body) {
        logger.info("onIndexedDataMessage");
    }
}
