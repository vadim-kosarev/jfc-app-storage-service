package dev.vk.jfc.app.storage.appstorage.repository;

import dev.vk.jfc.app.storage.appstorage.entities.IndexedDataItemEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface IndexedDataItemRepository extends CrudRepository<IndexedDataItemEntity, UUID> {
    IndexedDataItemEntity save(IndexedDataItemEntity item);
}
