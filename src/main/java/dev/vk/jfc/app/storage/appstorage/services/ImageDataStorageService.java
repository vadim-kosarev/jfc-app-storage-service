package dev.vk.jfc.app.storage.appstorage.services;

import java.util.Map;

public interface ImageDataStorageService {

    void onFrameImage(Map<String, Object> headers, byte[] payload);
    void onFrameFacesImage(Map<String, Object> headers, byte[] payload);
    void onIndexedDataMessage(Map<String, Object> headers, byte[] body);
}
