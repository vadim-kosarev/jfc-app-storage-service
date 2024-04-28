package dev.vk.jfc.app.storage.appstorage.tmp;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface LeftEntityRepository extends CrudRepository<OneToOneLeftEntity, UUID> {
    OneToOneLeftEntity save(OneToOneLeftEntity entity);
}
