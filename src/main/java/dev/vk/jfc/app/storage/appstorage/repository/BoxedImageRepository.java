package dev.vk.jfc.app.storage.appstorage.repository;

import dev.vk.jfc.app.storage.appstorage.entities.BoxedImageEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface BoxedImageRepository  extends CrudRepository<BoxedImageEntity, UUID> {
    BoxedImageEntity save(BoxedImageEntity boxedImageEntity);
}
