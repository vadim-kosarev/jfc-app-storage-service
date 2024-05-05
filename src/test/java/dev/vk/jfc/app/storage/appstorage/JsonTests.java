package dev.vk.jfc.app.storage.appstorage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.vk.jfc.app.storage.appstorage.dto.IndexedDataItemDto;
import dev.vk.jfc.app.storage.appstorage.entities.IndexedDataItemEntity;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.List;

@SpringBootTest
public class JsonTests {

    private final static Logger logger = LoggerFactory.getLogger(JsonTests.class);

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ModelMapper modelWrapper;

    @BeforeAll
    static void setUp() {
    }

    @Test
    @SneakyThrows
    void testJSON_Serialize() {
        IndexedDataItemEntity entity = new IndexedDataItemEntity();
        entity.setFaceIndex(999);
        entity.setDetection(0.7777777f);

        String jsonStr = objectMapper.writeValueAsString(entity);
        logger.info("Json:\n{}", jsonStr);

    }

    @Test
    @SneakyThrows
    void testJSON_00() {
        URL dataSrc = new URL(null, "classpath:json-test-00.json", new Handler(ClassLoader.getSystemClassLoader()));
        IndexedDataItemDto obj = objectMapper.readValue(dataSrc, IndexedDataItemDto.class);
        logger.info("+++ Object: {}", obj);

        IndexedDataItemEntity entity = modelWrapper.map(obj, IndexedDataItemEntity.class);
        logger.info("+++ Mapped entity: {}", entity);

    }

    @SneakyThrows
    @Test
    void testJSON() {
        URL dataSrc = new URL(null, "classpath:json-test-01.json", new Handler(ClassLoader.getSystemClassLoader()));
        //List<IndexedDataItemDto> obj = objectMapper.readValue(dataSrc, List.class);
        List<IndexedDataItemDto> obj = objectMapper.readValue(dataSrc,
                new TypeReference<List<IndexedDataItemDto>>() {}
        );
        logger.info("obj: {}", obj);
    }

    /**
     * A {@link URLStreamHandler} that handles resources on the classpath.
     */
    public static class Handler extends URLStreamHandler {
        /**
         * The classloader to find resources from.
         */
        private final ClassLoader classLoader;

        public Handler() {
            this.classLoader = getClass().getClassLoader();
        }

        public Handler(ClassLoader classLoader) {
            this.classLoader = classLoader;
        }

        @Override
        protected URLConnection openConnection(URL u) throws IOException {
            final URL resourceUrl = classLoader.getResource(u.getPath());
            return resourceUrl.openConnection();
        }
    }

}
