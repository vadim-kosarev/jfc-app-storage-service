package dev.vk.jfc.app.storage.appstorage;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.vk.jfc.app.storage.appstorage.entities.ImageEntity;
import dev.vk.jfc.app.storage.appstorage.repository.ImageRepository;
import dev.vk.jfc.app.storage.appstorage.services.ImageDataStorageService02;
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

import java.io.IOException;
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
    void test_02_onFrameImage() {
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

        byte[] getImage = storageService.getObject(found.getS3Path());
        assertEquals(payload.length, getImage.length);
        logger.info("Checked images size: OK");
    }

    @Test
    @SneakyThrows
    void test_05_onFrameFaces() {
        // setup
        String headersFile = "/msg05-q-indexed-images-processed-frame-faces-headers.json";
        String payloadFile = "/msg05-q-indexed-images-processed-frame-faces-payload.txt";
        test_onFrameFace_Arg(headersFile, payloadFile);
    }

    @Test
    @SneakyThrows
    void test_onFrameFace_03() {
        // setup
        String headersFile = "/msg03-q-indexed-images-processed-frame-face-headers.json";
        String payloadFile = "/msg03-q-indexed-images-processed-frame-face-payload.txt";
        test_onFrameFace_Arg(headersFile, payloadFile);
    }

    @Test
    @SneakyThrows
    void test_onFrameFace_04() {
        // setup
        String headersFile = "/msg04-q-indexed-images-processed-frame-face-headers.json";
        String payloadFile = "/msg04-q-indexed-images-processed-frame-face-payload.txt";
        test_onFrameFace_Arg(headersFile, payloadFile);
    }


    private void test_onFrameFace_Arg(String headersFile, String payloadFile) throws IOException {
        HashMap<String, Object> headers =  jsonObjectMapper.readValue(getClassPathInputStream(headersFile), HashMap.class);
        byte[] payload = Base64.decode(getClassPathInputStream(payloadFile).readAllBytes());

        // run
        dataStorageService02.onFrameFacesImage(headers, payload);
        storageService.putObject(String.valueOf(headers.get(Jfc.K_S3PATH)), payload);
    }

    @Test
    @SneakyThrows
    void test_faces05_then_parent02() {
        test_05_onFrameFaces();
        test_02_onFrameImage();
    }

    @Test
    @SneakyThrows
    void test_5_4_3_2() {
        test_05_onFrameFaces();
        test_onFrameFace_04();
        test_onFrameFace_03();
        test_02_onFrameImage();
    }

    @Test
    @SneakyThrows
    void test_55_4_3_2() {
        test_05_onFrameFaces();
        test_05_onFrameFaces();
        test_onFrameFace_04();
        test_onFrameFace_03();
        test_02_onFrameImage();
    }

    @Test
    @SneakyThrows
    void test_555_222() {
        test_05_onFrameFaces();
        test_05_onFrameFaces();
        test_05_onFrameFaces();
        test_02_onFrameImage();
        test_02_onFrameImage();
        test_02_onFrameImage();
    }

    private InputStream getClassPathInputStream(String path) {
        return getClass().getResourceAsStream(path);
    }

}
