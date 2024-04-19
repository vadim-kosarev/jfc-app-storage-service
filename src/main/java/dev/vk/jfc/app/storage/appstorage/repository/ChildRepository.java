package dev.vk.jfc.app.storage.appstorage.repository;

import dev.vk.jfc.app.storage.appstorage.dto.Child;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ChildRepository extends CrudRepository<Child, UUID> {
    Child save(Child child);
}
