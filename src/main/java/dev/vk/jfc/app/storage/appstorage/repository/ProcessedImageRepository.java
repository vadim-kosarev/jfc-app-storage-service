package dev.vk.jfc.app.storage.appstorage.repository;

import dev.vk.jfc.app.storage.appstorage.entities.ImageEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ProcessedImageRepository extends CrudRepository<ImageEntity, UUID> {
    ImageEntity save (ImageEntity arg);
}
