package dev.vk.jfc.app.storage.appstorage.repository;

import dev.vk.jfc.app.storage.appstorage.entities.ImageDataItemEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ImageDataItemRepository extends CrudRepository<ImageDataItemEntity, UUID> {
    ImageDataItemEntity save(ImageDataItemEntity data);
}
