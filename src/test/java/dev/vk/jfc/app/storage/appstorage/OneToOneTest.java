package dev.vk.jfc.app.storage.appstorage;

import dev.vk.jfc.app.storage.appstorage.tmp.OneToOneService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class OneToOneTest {

    private static final Logger logger = LoggerFactory.getLogger(OneToOneTest.class);

    @Autowired
    private OneToOneService theService;

    @Test
    public void test01() {
        UUID leftUuid = theService.run01();
        theService.run02(leftUuid);
        logger.info("Finished");
    }

}
