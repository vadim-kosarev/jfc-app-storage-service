package dev.vk.jfc.app.storage.appstorage.repository;

import dev.vk.jfc.app.storage.appstorage.entities.ImageData;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ImageDataRepository extends CrudRepository<ImageData, UUID> {
    ImageData save(ImageData entity);
}
