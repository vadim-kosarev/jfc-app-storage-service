package dev.vk.jfc.app.storage.appstorage.repository;

import dev.vk.jfc.app.storage.appstorage.entities.ImageDataEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ImageDataRepository extends CrudRepository<ImageDataEntity, UUID> {
    ImageDataEntity save(ImageDataEntity entity);
}
