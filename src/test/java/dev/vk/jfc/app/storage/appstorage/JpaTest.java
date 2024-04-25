package dev.vk.jfc.app.storage.appstorage;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.vk.jfc.app.storage.appstorage.entities.BaseEntity;
import dev.vk.jfc.app.storage.appstorage.entities.ImageEntity;
import dev.vk.jfc.app.storage.appstorage.repository.BaseObjectRepository;
import dev.vk.jfc.app.storage.appstorage.repository.ImageRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JpaTest {

    private final static Logger logger = LoggerFactory.getLogger(JpaTest.class);

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @SneakyThrows
    void test01() {
        Iterable<ImageEntity> all = imageRepository.findAll();
        for (ImageEntity imageEntity : all) {
            logger.info("=== Image: `{}` : {}",
                    imageEntity.getSource(),
                    objectMapper.writeValueAsString(imageEntity));
        }
    }
}
