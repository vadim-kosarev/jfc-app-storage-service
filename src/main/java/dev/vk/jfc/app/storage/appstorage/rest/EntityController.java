package dev.vk.jfc.app.storage.appstorage.rest;

import dev.vk.jfc.app.storage.appstorage.entities.ImageEntity;
import dev.vk.jfc.app.storage.appstorage.repository.ImageRepository;
import dev.vk.jfc.app.storage.appstorage.rest.dto.ImageDTO;
import dev.vk.jfc.app.storage.appstorage.services.ImageDataStorageService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class EntityController {


    private final ImageDataStorageService imageDataStorageService;
    private final ImageRepository imageRepository;
    private final ModelMapper dtoMapper;

    @GetMapping(value = "/imageEntity/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ImageDTO getImageEntity(@PathVariable String uuid) {
        ImageEntity entity = imageRepository.findById(UUID.fromString(uuid)).orElse(null);
        ImageDTO dto = dtoMapper.map(entity, ImageDTO.class);
        return dto;
    }
}
