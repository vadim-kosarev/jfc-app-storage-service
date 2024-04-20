package dev.vk.jfc.app.storage.appstorage.repository;

import dev.vk.jfc.app.storage.appstorage.entities.BndBoxesImage;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ProcessedImageBndBoxesObjectRepository extends CrudRepository<BndBoxesImage, UUID> {

    BndBoxesImage save(BndBoxesImage arg);

}
