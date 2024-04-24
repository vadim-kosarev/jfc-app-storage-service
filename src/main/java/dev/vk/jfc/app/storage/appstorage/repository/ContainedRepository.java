package dev.vk.jfc.app.storage.appstorage.repository;

import dev.vk.jfc.app.storage.appstorage.entities.samples.Container;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ContainedRepository extends CrudRepository<Container, UUID> {
    Container save(Container arg);
}
