package dev.vk.jfc.app.storage.appstorage;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.vk.jfc.app.storage.appstorage.entities.ImageEntity;
import dev.vk.jfc.app.storage.appstorage.services.ImageDataStorageService;
import dev.vk.jfc.jfccommon.Jfc;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.InputStream;
import java.util.HashMap;
import java.util.UUID;

@SpringBootTest
public class ImageDataStorageServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(ImageDataStorageServiceTest.class);

    @Autowired
    private ImageDataStorageService imageDataStorageService;

    @Autowired
    private ObjectMapper jsonObjectMapper;

    @Test
    @SneakyThrows
    void test_02_imageEntity() {
        String headersFile = "/msg02-q-indexed-images-processed-frame-headers.json";
        HashMap<String, Object> headers =
                jsonObjectMapper.readValue(getClassPathInputStream(headersFile), HashMap.class);
        logger.info("message headers:\n{}", headers);
        ImageEntity entity = imageDataStorageService.getImageEntity(headers);
        logger.info("Created entity: {}", entity.getId());
    }

    @Test
    @SneakyThrows
    void test_03_04_imageEntity() {
        String headersFile3 = "/msg03-q-indexed-images-processed-frame-face-headers.json";
        String headersFile4 = "/msg04-q-indexed-images-processed-frame-face-headers.json";

        UUID parentUuid1;
        UUID parentUuid2;

        {
            String headersFile = headersFile3;
            HashMap<String, Object> headers =
                    jsonObjectMapper.readValue(getClassPathInputStream(headersFile), HashMap.class);
            ImageEntity entity = imageDataStorageService.getImageFaceEntity(headers);
            parentUuid1 = UUID.fromString(String.valueOf(headers.get(Jfc.K_PARENT_UUID)));
        }

        {
            String headersFile = headersFile4;
            HashMap<String, Object> headers =
                    jsonObjectMapper.readValue(getClassPathInputStream(headersFile), HashMap.class);
            ImageEntity entity = imageDataStorageService.getImageFaceEntity(headers);
            parentUuid2 = UUID.fromString(String.valueOf(headers.get(Jfc.K_PARENT_UUID)));
        }


    }

    @Test
    @SneakyThrows
    void test_05_imageEntity() {
        String headersFile3 = "/msg05-q-indexed-images-processed-frame-faces-headers.json";

        String headersFile = headersFile3;
        HashMap<String, Object> headers =
                jsonObjectMapper.readValue(getClassPathInputStream(headersFile), HashMap.class);
        ImageEntity entity = imageDataStorageService.getImageFaceEntity(headers);
        UUID parentUuid = UUID.fromString(String.valueOf(headers.get(Jfc.K_PARENT_UUID)));

    }

    @Test
    @SneakyThrows
    void test_02_05_version01() {
        test_02_imageEntity();
        test_03_04_imageEntity();
        test_05_imageEntity();
    }

    @Test
    @SneakyThrows
    void test_02_05_version02() {
        test_03_04_imageEntity();
        test_05_imageEntity();
        test_02_imageEntity();
    }

    @Test
    @SneakyThrows
    void test_02_05_version03() {
        test_05_imageEntity();
        test_03_04_imageEntity();
        test_02_imageEntity();
    }

    private InputStream getClassPathInputStream(String path) {
        return getClass().getResourceAsStream(path);
    }


}
