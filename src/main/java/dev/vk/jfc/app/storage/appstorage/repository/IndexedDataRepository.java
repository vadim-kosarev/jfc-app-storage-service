package dev.vk.jfc.app.storage.appstorage.repository;

import dev.vk.jfc.app.storage.appstorage.entities.IndexedData;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface IndexedDataRepository extends CrudRepository<IndexedData, UUID> {
    IndexedData save(IndexedData arg);
}
