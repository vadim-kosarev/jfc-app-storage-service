package dev.vk.jfc.app.storage.appstorage.repository;

import dev.vk.jfc.app.storage.appstorage.dto.ProcessedImageObject;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ProcessedImageObjectRepository extends CrudRepository<ProcessedImageObject, UUID> {
    ProcessedImageObject  save (ProcessedImageObject arg);
}
