package dev.vk.jfc.app.storage.appstorage.repository;

import dev.vk.jfc.app.storage.appstorage.entities.BoxedImage;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface BoxedImageRepository  extends CrudRepository<BoxedImage, UUID> {
    BoxedImage save(BoxedImage boxedImage);
}
