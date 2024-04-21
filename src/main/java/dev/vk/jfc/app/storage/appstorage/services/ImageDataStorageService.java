package dev.vk.jfc.app.storage.appstorage.services;

import dev.vk.jfc.app.storage.appstorage.entities.HeadersEntity;
import dev.vk.jfc.app.storage.appstorage.entities.ImageData;
import dev.vk.jfc.app.storage.appstorage.entities.ImageDataItem;
import dev.vk.jfc.app.storage.appstorage.entities.ProcessedImage;
import dev.vk.jfc.app.storage.appstorage.repository.ImageDataItemRepository;
import dev.vk.jfc.app.storage.appstorage.repository.ImageDataRepository;
import dev.vk.jfc.app.storage.appstorage.repository.ProcessedImageRepository;
import dev.vk.jfc.jfccommon.Jfc;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ImageDataStorageService {

    private final ProcessedImageRepository processedImageRepository;
    private final ImageDataRepository imageDataRepository;
    private final ImageDataItemRepository imageDataItemRepository;



    protected void fillInHeaderData(HeadersEntity image, Map<String, Object> headers) {
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
        if (lookForEntity.isPresent()) {
            return pImg;
        } else {
            pImg.setId(uuid);
            return processedImageRepository.save(pImg);
        }
    }

    @Transactional
    protected ImageData getImageData(UUID uuid) {
        Optional<ImageData> lookForEntity = imageDataRepository.findById(uuid);
        ImageData imgData = lookForEntity.orElseGet(ImageData::new);
        imgData.setId(uuid);

        if (imgData.getElements() == null) {
            imgData.setElements(new ArrayList<>());
        }
        imageDataRepository.save(imgData);
        return imgData;

    }

    @Transactional
    protected ImageDataItem getImageDataItem(UUID uuid) {
        Optional<ImageDataItem> lookForEntity = imageDataItemRepository.findById(uuid);
        if (lookForEntity.isPresent()) {
            return lookForEntity.get();
        } else {
            ImageDataItem pEnt = new ImageDataItem();
            pEnt.setId(uuid);
            return imageDataItemRepository.save(pEnt);
        }
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
    public void onImageData(Message message) {
        Map<String, Object> headers = message.getMessageProperties().getHeaders();
        UUID uuid = UUID.fromString(String.valueOf(headers.get(Jfc.K_UUID)));

        UUID parentUuid = UUID.fromString(String.valueOf(headers.get(Jfc.K_PARENT_UUID)));
        ProcessedImage parentImage = getRootProcessedImage(parentUuid);

        ImageData imgData = getImageData(uuid);
        fillInHeaderData(imgData, headers);
        imgData.setProcessedImage(parentImage);
        imageDataRepository.save(imgData);
    }

    @Transactional
    public void onImageDataItem(Message message) {
        Map<String, Object> headers = message.getMessageProperties().getHeaders();
        UUID uuid = UUID.fromString(String.valueOf(headers.get(Jfc.K_UUID)));

        UUID parentUuid = UUID.fromString(String.valueOf(headers.get(Jfc.K_PARENT_UUID)));
        ImageData parentImageData = getImageData(parentUuid);

        ImageDataItem dataItem = getImageDataItem(uuid);
        fillInHeaderData(dataItem, headers);
        parentImageData.getElements().add(dataItem);

        imageDataRepository.save(parentImageData);
    }
}
