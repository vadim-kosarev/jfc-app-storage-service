package dev.vk.jfc.app.storage.appstorage.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.vk.jfc.app.storage.appstorage.dto.ImageDataItemDto;
import dev.vk.jfc.app.storage.appstorage.entities.FloatArrayItemEntity;
import dev.vk.jfc.app.storage.appstorage.entities.IndexedDataItemEntity;
import dev.vk.jfc.app.storage.appstorage.entities.data.ArrayItemId;
import io.minio.MinioClient;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    private final static Logger logger = LoggerFactory.getLogger(AppConfig.class);

    @Value("${app.minio.endpoint}")
    private String minioEndpoint;

    @Value("${app.minio.username}")
    private String minioUsername;

    @Value("${app.minio.password}")
    private String minioPassword;

    @Bean
    public MinioClient getMinioClient() {
        return MinioClient
                .builder()
                .endpoint(minioEndpoint)
                .credentials(minioUsername, minioPassword)
                .build();
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        PropertyMap<ImageDataItemDto, IndexedDataItemEntity> imageBoxMappingMap =
                new PropertyMap<ImageDataItemDto, IndexedDataItemEntity>() {
                    @Override
                    protected void configure() {
                        map().setImgBox_p1_x(source.getFaceBox().getP1().getX());
                        map().setImgBox_p1_y(source.getFaceBox().getP1().getY());
                        map().setImgBox_p2_x(source.getFaceBox().getP2().getX());
                        map().setImgBox_p2_y(source.getFaceBox().getP2().getY());
                    }
                };
        modelMapper.addMappings(imageBoxMappingMap);
        return modelMapper;
    }

}
