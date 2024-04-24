package dev.vk.jfc.app.storage.appstorage.repository;

import dev.vk.jfc.app.storage.appstorage.entities.IndexedDataEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface IndexedDataRepository extends CrudRepository<IndexedDataEntity, UUID> {
    IndexedDataEntity save(IndexedDataEntity arg);
}
