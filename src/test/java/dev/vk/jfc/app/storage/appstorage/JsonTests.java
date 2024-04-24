package dev.vk.jfc.app.storage.appstorage;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.vk.jfc.app.storage.appstorage.entities.ImageDataItem;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.ArrayList;
import java.util.List;

public class JsonTests {

    private final static Logger logger = LoggerFactory.getLogger(JsonTests.class);

    private static ObjectMapper objectMapper;

    @BeforeAll
    static void setUp() {
        objectMapper = new ObjectMapper();

    }

    @Test
    @SneakyThrows
    void testJSON_Serialize() {
        ImageDataItem entity = new ImageDataItem();
        entity.setFaceIndex(999);
        entity.setDetection(0.7777777f);

        String jsonStr = objectMapper.writeValueAsString(entity);
        logger.info("Json:\n{}", jsonStr);
    }

    @Test
    @SneakyThrows
    void testJSON_00() {
        URL dataSrc = new URL(null, "classpath:json-test-00.json", new Handler(ClassLoader.getSystemClassLoader()));
        Object obj  = objectMapper.readValue(dataSrc, ImageDataItem.class);
        logger.info("Object: {}", obj);
    }

    @SneakyThrows
    @Test
    void testJSON() {
        URL dataSrc = new URL(null, "classpath:json-test-01.json", new Handler(ClassLoader.getSystemClassLoader()));
        List<Object> obj  = objectMapper.readValue(dataSrc, ArrayList.class);
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
