package dev.vk.jfc.app.storage.appstorage;

import dev.vk.jfc.app.storage.appstorage.entities.BaseEntity;
import dev.vk.jfc.app.storage.appstorage.repository.BaseObjectRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JpaTest {

    private final static Logger logger = LoggerFactory.getLogger(JpaTest.class);

    @Autowired
    private BaseObjectRepository baseObjectRepository;;

    @Test
    void test01() {
        Iterable<BaseEntity> all = baseObjectRepository.findAll();
        for (BaseEntity be : all) {
            logger.info("++ {} #{} ({})", be.getClass().getName(), be.getId(), be.getContainer().getId());
        }
    }

}
