package dev.vk.jfc.app.storage.appstorage.tmp;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface RightEntityRepository extends CrudRepository<OneToOneRightEntity, UUID> {
    OneToOneRightEntity save(OneToOneRightEntity entity);
}
