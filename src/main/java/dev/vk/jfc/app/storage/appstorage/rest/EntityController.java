package dev.vk.jfc.app.storage.appstorage.rest;

import dev.vk.jfc.app.storage.appstorage.dto.ImageDto;
import dev.vk.jfc.app.storage.appstorage.entities.ImageEntity;
import dev.vk.jfc.app.storage.appstorage.entities.IndexedDataEntity;
import dev.vk.jfc.app.storage.appstorage.repository.ImageRepository;
import dev.vk.jfc.app.storage.appstorage.services.ImageDataStorageService01;
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


    private final ImageDataStorageService01 imageDataStorageService01;
    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;

    @GetMapping(value = "/imageEntity/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ImageDto getImageEntity(@PathVariable UUID uuid) {
        ImageEntity entity = imageRepository.findById(uuid).orElseThrow();
        ImageDto dto = modelMapper.map(entity, ImageDto.class);
        return dto;
    }
}
