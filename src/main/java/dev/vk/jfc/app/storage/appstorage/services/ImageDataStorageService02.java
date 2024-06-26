package dev.vk.jfc.app.storage.appstorage.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.vk.jfc.app.storage.appstorage.dto.FaceBox;
import dev.vk.jfc.app.storage.appstorage.dto.ImageDto;
import dev.vk.jfc.app.storage.appstorage.dto.ImagePoint;
import dev.vk.jfc.app.storage.appstorage.dto.IndexedDataItemDto;
import dev.vk.jfc.app.storage.appstorage.entities.*;
import dev.vk.jfc.app.storage.appstorage.entities.data.ArrayItemId;
import dev.vk.jfc.app.storage.appstorage.repository.ImageRepository;
import dev.vk.jfc.app.storage.appstorage.repository.IndexedDataItemRepository;
import dev.vk.jfc.app.storage.appstorage.repository.IndexedDataRepository;
import dev.vk.jfc.jfccommon.Jfc;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Primary
@AllArgsConstructor
public class ImageDataStorageService02 implements ImageDataStorageService {

    private final static Logger logger = LoggerFactory.getLogger(ImageDataStorageService02.class);

    private final ImageRepository imageRepository;
    private final IndexedDataRepository indexedDataRepository;
    private final IndexedDataItemRepository indexedDataItemRepository;
    private final ObjectMapper jsonObjectMapper;
    private final ModelMapper modelMapper;

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
    @Transactional
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
            imageEntity.setLabel("Created ImageEntity as new: %s".formatted(uuid));
        } else {
            imageEntity.setLabel("Found ImageEntity from DB: %s".formatted(uuid));
        }
        if (imageEntity.getElements() == null) {
            imageEntity.setElements(new ArrayList<>());
        }
        return imageEntity;
    }

    @Override
    @Transactional
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
    @SneakyThrows
    public void onIndexedDataMessage(Map<String, Object> headers, byte[] payload) {
        logger.info("onIndexedDataMessage");
        UUID uuid = UUID.fromString(String.valueOf(headers.get(Jfc.K_UUID)));
        IndexedDataEntity me = getIndexedDataEntity(uuid);
        for (BaseEntity elem : me.getElements()) {
            indexedDataItemRepository.deleteById(elem.getId());
        }
        me.setElements(new ArrayList<BaseEntity>());
        fillInHeaderData(me, headers);

        logger.info("Entity: {}", me);

        UUID parentUuid = UUID.fromString(String.valueOf(headers.get(Jfc.K_PARENT_UUID)));
        ImageEntity parentImageEntity = getImageEntity(headers, parentUuid);
        parentImageEntity = imageRepository.save(parentImageEntity);
        me.setImageEntity(parentImageEntity);

        // ####
        List<IndexedDataItemDto> theList =
                jsonObjectMapper.readValue(
                        payload,
                        new TypeReference<List<IndexedDataItemDto>>() {
                        }
                );

        for (IndexedDataItemDto item : theList) {

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
            logger.info("Adding itemEntity: {}", itemEntity.getId());

            me.getElements().add(itemEntity);
            itemEntity.setContainer(me);
        }

        me = indexedDataRepository.save(me);
    }

    private @NotNull IndexedDataEntity getIndexedDataEntity(UUID uuid) {
        IndexedDataEntity entity = indexedDataRepository.findById(uuid).orElseGet(IndexedDataEntity::new);
        if (entity.getId() == null) {
            entity.setId(uuid);
            entity.setLabel("Created new IndexedDataEntity: %s".formatted(uuid));
        } else {
            entity.setLabel("Found in DB IndexedDataEntity: %s".formatted(uuid));
        }

        if (entity.getElements() == null) {
            entity.setElements(new ArrayList<BaseEntity>());
        }

        return entity;
    }

    @Override
    public ImageDto getById(UUID uuid) {
        ImageEntity entity = imageRepository.findById(uuid).orElseThrow();
        IndexedDataEntity dataEntity = entity.getIndexedDataEntity();
        for (BaseEntity be : dataEntity.getElements()) {
            IndexedDataItemEntity die = (IndexedDataItemEntity) be;

            die.getFaceVector().sort((o1, o2) -> o1.getItemId().getInd() - o2.getItemId().getInd());
            IndexedDataItemDto dit = modelMapper.map(die, IndexedDataItemDto.class);

                              // ###
            dit.setFaceBox(new FaceBox(
                    new ImagePoint(die.getImgBox_p1_x(), die.getImgBox_p1_y()),
                    new ImagePoint(die.getImgBox_p2_x(), die.getImgBox_p2_y())
            ));
        }
        ImageDto dto = modelMapper.map(entity, ImageDto.class);
        return dto;
    }
}
