package dev.vk.jfc.app.storage.appstorage.repository;

import dev.vk.jfc.app.storage.appstorage.entities.ProcessedImage;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ProcessedImageObjectRepository extends CrudRepository<ProcessedImage, UUID> {
    ProcessedImage save (ProcessedImage arg);
}
