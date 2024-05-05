package dev.vk.jfc.app.storage.appstorage;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.vk.jfc.app.storage.appstorage.entities.ImageEntity;
import dev.vk.jfc.app.storage.appstorage.entities.IndexedDataEntity;
import dev.vk.jfc.app.storage.appstorage.repository.ImageRepository;
import dev.vk.jfc.app.storage.appstorage.services.ImageDataStorageService01;
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
public class ImageDataStorageService01Test {

    private static final Logger logger = LoggerFactory.getLogger(ImageDataStorageService01Test.class);

    @Autowired
    private ImageDataStorageService01 imageDataStorageService01;

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
        ImageEntity entity = imageDataStorageService01.onImageMessage(headers, true);
        logger.info("Created entity: {}", entity.getId());
        assertValues(true);
    }

    @Test
    @SneakyThrows
    void test_03_04_imageEntity() {
        test_03();
        test_04();
        ImageEntity entityToCheck = getCheckImageEntity();
        logger.info("Collection of `{}`: {} element(s)", entityToCheck.getId(), entityToCheck.getElements().size());
        assertValues(false);
    }

    @SneakyThrows
    void test_03() {
        test_034_path("/msg03-q-indexed-images-processed-frame-face-headers.json");
    }

    @SneakyThrows
    void test_04() {
        test_034_path("/msg04-q-indexed-images-processed-frame-face-headers.json");
    }

    private void test_034_path(String headersFile3) throws IOException {
        HashMap<String, Object> headers =
                jsonObjectMapper.readValue(getClassPathInputStream(headersFile3), HashMap.class);
        ImageEntity entity = imageDataStorageService01.getImageFaceEntity(headers, true);
        entity = imageRepository.save(entity);
        assertEquals(entity.getContainer().getId(), getCheckImageUuid());
    }

    @Test
    @SneakyThrows
    void test_05_imageEntity() {
        String headersFile3 = "/msg05-q-indexed-images-processed-frame-faces-headers.json";

        HashMap<String, Object> headers =
                jsonObjectMapper.readValue(getClassPathInputStream(headersFile3), HashMap.class);
        ImageEntity entity = imageDataStorageService01.getImageFaceEntity(headers, true);
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
        return imageRepository.findById(uuid).orElse(new ImageEntity(uuid));
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

        IndexedDataEntity entity = imageDataStorageService01.getIndexedDataEntity(headers, payload);
        logger.info("Finished test_01_indexed_data ... {}", entity.getId());
    }

    @Test
    @SneakyThrows
    void test_02_05__05_01_version01() {
        test_02_imageEntity();
        test_03_04_imageEntity();
        test_05_imageEntity();
        test_01_indexed_data();
        assertValues(true);
    }


    @Test
    @SneakyThrows
    void test_01_02_03_04_05_version01() {
        test_01_indexed_data();
        test_02_imageEntity();
        test_03_04_imageEntity();
        test_05_imageEntity();
        assertValues(true);
    }
}
