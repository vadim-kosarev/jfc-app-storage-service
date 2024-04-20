package dev.vk.jfc.app.storage.appstorage.repository;

import dev.vk.jfc.app.storage.appstorage.dto.ProcessedImageBndBoxesObject;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ProcessedImageBndBoxesObjectRepository extends CrudRepository<ProcessedImageBndBoxesObject, UUID> {

    ProcessedImageBndBoxesObject save(ProcessedImageBndBoxesObject arg);

}
