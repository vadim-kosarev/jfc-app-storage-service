package dev.vk.jfc.app.storage.appstorage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.Serializers;
import dev.vk.jfc.app.storage.appstorage.entities.BaseEntity;
import dev.vk.jfc.app.storage.appstorage.entities.ImageEntity;
import dev.vk.jfc.app.storage.appstorage.repository.BaseEntityRepository;
import dev.vk.jfc.app.storage.appstorage.repository.ImageRepository;
import lombok.SneakyThrows;
import org.hibernate.metamodel.internal.RuntimeMetamodelsImpl;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class JpaTest {

    private final static Logger logger = LoggerFactory.getLogger(JpaTest.class);

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BaseEntityRepository baseEntityRepository;


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

    @Test
    void testVersions() {
        BaseEntity ent = new BaseEntity();
        UUID uuid = UUID.randomUUID();
        ent.setId(uuid);
        ent.setLabel("Version One");
        baseEntityRepository.save(ent);

        BaseEntity b1 = baseEntityRepository.findById(uuid).orElseThrow();
        BaseEntity b2 = baseEntityRepository.findById(uuid).orElseThrow();

        b1.setLabel("Version Two");
        b2.setLabel("Version Three");

        baseEntityRepository.save(b1);
        try {
            baseEntityRepository.save(b2);
        } catch (ObjectOptimisticLockingFailureException e) {
            return;
        }
        throw new RuntimeException("Optimistic lock didn't work");
    }
}
