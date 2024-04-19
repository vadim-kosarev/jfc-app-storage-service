package dev.vk.jfc.app.storage.appstorage.repository;

import dev.vk.jfc.app.storage.appstorage.dto.Contained;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ContainedRepository extends CrudRepository<Contained, UUID> {
    Contained save(Contained arg);
}
