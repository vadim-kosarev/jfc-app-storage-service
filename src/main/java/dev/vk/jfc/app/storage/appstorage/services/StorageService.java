package dev.vk.jfc.app.storage.appstorage.services;

public interface StorageService {
    void putObject(String s3Path, byte[] body);
}
