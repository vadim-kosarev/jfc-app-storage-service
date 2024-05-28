package dev.vk.jfc.app.storage.appstorage.repository;

import dev.vk.jfc.app.storage.appstorage.entities.BaseEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface BaseEntityRepository extends CrudRepository<BaseEntity, UUID> {

    BaseEntity save(BaseEntity baseEntity);

}
