package dev.vk.jfc.app.storage.appstorage.service;

import dev.vk.jfc.app.storage.appstorage.dto.JpgData;
import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.UUID;

public interface JpgDataRepository extends Repository<UUID, JpgData> {
    JpgData save(JpgData arg);

    Optional<JpgData> findById(UUID id);
}
