package dev.vk.jfc.app.storage.appstorage.config;

import io.minio.MinioClient;
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

}
