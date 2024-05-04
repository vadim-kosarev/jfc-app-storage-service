package dev.vk.jfc.app.storage.appstorage;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.vk.jfc.app.storage.appstorage.entities.ImageEntity;
import dev.vk.jfc.app.storage.appstorage.repository.ImageRepository;
import dev.vk.jfc.app.storage.appstorage.services.ImageDataStorageService02;
import dev.vk.jfc.app.storage.appstorage.services.S3StorageService;
import dev.vk.jfc.app.storage.appstorage.services.StorageService;
import dev.vk.jfc.jfccommon.Jfc;
import lombok.SneakyThrows;
import org.bouncycastle.util.encoders.Base64;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.InputStream;
import java.util.HashMap;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ImageDataStorageService02Test {

    private static final Logger logger = LoggerFactory.getLogger(ImageDataStorageService02Test.class);

    @Autowired
    private ObjectMapper jsonObjectMapper;

    @Autowired
    private ImageDataStorageService02 dataStorageService02;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private StorageService storageService;

    @AfterAll
    static void afterAll() {
        logger.info("Finished");
    }

    @Test
    @SneakyThrows
    void test_onFrameImage() {
        // setup
        String headersFile = "/msg02-q-indexed-images-processed-frame-headers.json";
        String payloadFile = "/msg02-q-indexed-images-processed-frame-payload.txt";
        HashMap<String, Object> headers =  jsonObjectMapper.readValue(getClassPathInputStream(headersFile), HashMap.class);
        byte[] payload = Base64.decode(getClassPathInputStream(payloadFile).readAllBytes());

        // run
        dataStorageService02.onFrameImage(headers, payload);
        storageService.putObject(String.valueOf(headers.get(Jfc.K_S3PATH)), payload);

        // Check
        UUID uuid = UUID.fromString(String.valueOf(headers.get(Jfc.K_UUID)));
        ImageEntity found = imageRepository.findById(uuid).orElseThrow();
        assertEquals(uuid, found.getId());
        logger.info("Found image {}", jsonObjectMapper.writeValueAsString(found));
    }

    private InputStream getClassPathInputStream(String path) {
        return getClass().getResourceAsStream(path);
    }

}
