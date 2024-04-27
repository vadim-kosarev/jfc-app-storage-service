package dev.vk.jfc.app.storage.appstorage;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.vk.jfc.app.storage.appstorage.entities.ImageEntity;
import dev.vk.jfc.app.storage.appstorage.entities.IndexedDataEntity;
import dev.vk.jfc.app.storage.appstorage.repository.ImageRepository;
import dev.vk.jfc.app.storage.appstorage.services.ImageDataStorageService;
import dev.vk.jfc.jfccommon.Jfc;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ImageDataStorageServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(ImageDataStorageServiceTest.class);

    @Autowired
    private ImageDataStorageService imageDataStorageService;

    @Autowired
    private ObjectMapper jsonObjectMapper;

    @Autowired
    private ImageRepository imageRepository;

    @Test
    @SneakyThrows
    void test_02_imageEntity() {
        String headersFile = "/msg02-q-indexed-images-processed-frame-headers.json";
        HashMap<String, Object> headers =
                jsonObjectMapper.readValue(getClassPathInputStream(headersFile), HashMap.class);
        logger.info("message headers:\n{}", headers);
        ImageEntity entity = imageDataStorageService.getImageEntity(headers);
        logger.info("Created entity: {}", entity.getId());
        assertValues(true);
    }

    @Test
    @SneakyThrows
    void test_03_04_imageEntity() {
        String headersFile3 = "/msg03-q-indexed-images-processed-frame-face-headers.json";
        String headersFile4 = "/msg04-q-indexed-images-processed-frame-face-headers.json";

        {
            String headersFile = headersFile3;
            HashMap<String, Object> headers =
                    jsonObjectMapper.readValue(getClassPathInputStream(headersFile), HashMap.class);
            ImageEntity entity = imageDataStorageService.getImageFaceEntity(headers);
            assertEquals(entity.getContainer().getId(), getCheckImageUuid());
        }

        {
            String headersFile = headersFile4;
            HashMap<String, Object> headers =
                    jsonObjectMapper.readValue(getClassPathInputStream(headersFile), HashMap.class);
            ImageEntity entity = imageDataStorageService.getImageFaceEntity(headers);
            assertEquals(entity.getContainer().getId(), getCheckImageUuid());
        }

        ImageEntity entityToCheck = getCheckImageEntity();
        logger.info("Collection of `{}`: {} element(s)", entityToCheck.getId(), entityToCheck.getElements().size());

        assertValues(false);
    }

    @Test
    @SneakyThrows
    void test_05_imageEntity() {
        String headersFile3 = "/msg05-q-indexed-images-processed-frame-faces-headers.json";

        HashMap<String, Object> headers =
                jsonObjectMapper.readValue(getClassPathInputStream(headersFile3), HashMap.class);
        ImageEntity entity = imageDataStorageService.getImageFaceEntity(headers);
        UUID parentUuid = UUID.fromString(String.valueOf(headers.get(Jfc.K_PARENT_UUID)));

        assertValues(false);
    }

    @Test
    void test_02_03_04_imageEntity() {
        test_02_imageEntity();
        test_03_04_imageEntity();
        assertValues(true);
//        assertEquals(2, getCheckImageEntity().getElements().size());
    }


    @Test
    @SneakyThrows
    void test_02_05_version01() {
        test_02_imageEntity();
        test_03_04_imageEntity();
        test_05_imageEntity();
        assertValues(true);
//        assertEquals(3, getCheckImageEntity().getElements().size());
    }

    @Test
    @SneakyThrows
    void test_02_05_version02() {
        test_03_04_imageEntity();
        test_05_imageEntity();
        test_02_imageEntity();
//        assertEquals(3, getCheckImageEntity().getElements().size());
    }

    @Test
    @SneakyThrows
    void test_02_05_version03() {
        test_05_imageEntity();
        test_03_04_imageEntity();
        test_02_imageEntity();
//        assertEquals(3, getCheckImageEntity().getElements().size());
    }

    ImageEntity getImageEntity(UUID uuid) {
        return imageRepository.findById(uuid).orElse(null);
    }

    void assertValues(boolean checkSavedValues) {
        ImageEntity entity = getCheckImageEntity();
        if (checkSavedValues) {
            assertEquals("out/orban_putin.jpg", entity.getSource());
            assertEquals("processed-frame", entity.getMessageType());
        }
        assertEquals(UUID.fromString("4766d9cb-623f-4767-84bc-c8e406408d17"), entity.getId());
        assertNotNull(entity.getElements());
        assertNull(entity.getContainer());
    }

    @SneakyThrows
    ImageEntity getImageEntity(String resouce) {
        UUID uuid = getCheckImageUuid();
        return getImageEntity(uuid);
    }

    private @NotNull UUID getCheckImageUuid() throws IOException {
        String headersFile = "/msg02-q-indexed-images-processed-frame-headers.json";
        HashMap<String, Object> headers =
                jsonObjectMapper.readValue(getClassPathInputStream(headersFile), HashMap.class);
        UUID uuid = UUID.fromString(String.valueOf(headers.get(Jfc.K_UUID)));
        return uuid;
    }

    ImageEntity getCheckImageEntity() {
        String headersFile = "/msg02-q-indexed-images-processed-frame-headers.json";
        return getImageEntity(headersFile);
    }


    private InputStream getClassPathInputStream(String path) {
        return getClass().getResourceAsStream(path);
    }

    @Test
    @SneakyThrows
    void test_01_indexed_data() {
        String headersFile = "/msg01-q-indexed-data-processed-frame-data-headers.json";
        String payloadFile = "/msg01-q-indexed-data-processed-frame-data-payload.json";

        HashMap<String, Object> headers = jsonObjectMapper.readValue(getClassPathInputStream(headersFile), HashMap.class);
        byte[] payload = getClassPathInputStream(payloadFile).readAllBytes();

        IndexedDataEntity entity = imageDataStorageService.getIndexedDataEntity(headers, payload);
        logger.info("Finished...");
    }

}
