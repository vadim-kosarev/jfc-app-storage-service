package dev.vk.jfc.app.storage.appstorage.repository;

import dev.vk.jfc.app.storage.appstorage.entities.ImageDataItem;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ImageDataItemRepository extends CrudRepository<ImageDataItem, UUID> {
    ImageDataItem save(ImageDataItem data);
}
