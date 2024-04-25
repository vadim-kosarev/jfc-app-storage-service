package dev.vk.jfc.app.storage.appstorage;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.vk.jfc.app.storage.appstorage.entities.ImageEntity;
import dev.vk.jfc.app.storage.appstorage.services.ImageDataStorageService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class ImageDataStorageServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(ImageDataStorageServiceTest.class);

    @Autowired
    private ImageDataStorageService imageDataStorageService;

    @Autowired
    private ObjectMapper jsonObjectMapper;

    @Test
    @SneakyThrows
    void test_01_imageEntity() {
        String headersFile = "/msg02-q-indexed-images-processed-frame-headers.json";
        HashMap<String, Object> headers =
                jsonObjectMapper.readValue(getClassPathInputStream(headersFile), HashMap.class);
        logger.info("message headers:\n{}", headers);
        ImageEntity entity = imageDataStorageService.getImageEntity(headers);
        logger.info("Created entity: {}", entity.getId());
    }

    private InputStream getClassPathInputStream(String path) {
        return getClass().getResourceAsStream(path);
    }


}
