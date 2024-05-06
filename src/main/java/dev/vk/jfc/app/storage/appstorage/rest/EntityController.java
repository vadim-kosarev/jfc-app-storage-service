package dev.vk.jfc.app.storage.appstorage.rest;

import dev.vk.jfc.app.storage.appstorage.dto.ImageDto;
import dev.vk.jfc.app.storage.appstorage.dto.ImageDtoLight;
import dev.vk.jfc.app.storage.appstorage.entities.ImageEntity;
import dev.vk.jfc.app.storage.appstorage.repository.ImageRepository;
import dev.vk.jfc.app.storage.appstorage.services.ImageDataStorageService01;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class EntityController {


    private final ImageDataStorageService01 imageDataStorageService01;
    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;

    @GetMapping(value = "/image-entity/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ImageDto getImageEntity(@PathVariable UUID uuid) {
        ImageEntity entity = imageRepository.findById(uuid).orElseThrow();

        ImageDto dto = modelMapper.map(entity, ImageDto.class);

        return dto;
    }

    @GetMapping(value = "/image-entities", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<ImageDtoLight> listAllImageEntity() {
        List<ImageEntity> entities = imageRepository.findRoots();

        Type listType = new TypeToken<List<ImageDtoLight>>() {}.getType();
        List<ImageDtoLight> theList = modelMapper.map(entities, listType);

        return theList;
    }
}
