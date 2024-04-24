package dev.vk.jfc.app.storage.appstorage.repository;

import dev.vk.jfc.app.storage.appstorage.entities.tmp_ImageEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ImageEntityRepository extends CrudRepository<tmp_ImageEntity, UUID> {
    tmp_ImageEntity save(tmp_ImageEntity entity);
}
