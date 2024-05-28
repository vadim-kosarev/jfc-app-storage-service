package dev.vk.jfc.app.storage.appstorage.mq;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class RabbitMQConfig {

    @Value("${app.rabbitmq.definitions.url}")
    private String importDefinitionsUrl;

    @Value("${app.rabbitmq.definitions.username}")
    private String importDefinitionsUser;

    @Value("${app.rabbitmq.definitions.password}")
    private String importDefinitionsPassword;

    @Value("${app.rabbitmq.definitions.template.path}")
    private String importDefinitionsTemplatePath;

}
