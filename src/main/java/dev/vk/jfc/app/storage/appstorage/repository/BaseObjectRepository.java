package dev.vk.jfc.app.storage.appstorage.repository;

import dev.vk.jfc.app.storage.appstorage.dto.BaseObject;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface BaseObjectRepository extends CrudRepository<BaseObject, UUID> {

    BaseObject save(BaseObject baseObject);

}
