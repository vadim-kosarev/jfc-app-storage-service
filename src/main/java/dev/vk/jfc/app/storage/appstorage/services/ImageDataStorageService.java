package dev.vk.jfc.app.storage.appstorage.services;

import dev.vk.jfc.app.storage.appstorage.dto.ImageDto;

import java.util.Map;
import java.util.UUID;

public interface ImageDataStorageService {

    void onFrameImage(Map<String, Object> headers, byte[] payload);
    void onFrameFacesImage(Map<String, Object> headers, byte[] payload);
    void onIndexedDataMessage(Map<String, Object> headers, byte[] body);

    ImageDto getById(UUID uuid);
}
