package dev.vk.jfc.app.storage.appstorage.repository;

import dev.vk.jfc.app.storage.appstorage.entities.ImageEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ImageRepository extends CrudRepository<ImageEntity, UUID> {
    ImageEntity save (ImageEntity arg);

    @Query("select e from ImageEntity as e where e.container is null")
    List<ImageEntity> findRoots();
}
