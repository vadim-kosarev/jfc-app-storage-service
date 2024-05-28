package dev.vk.jfc.app.storage.appstorage.mq;

import dev.vk.jfc.app.storage.appstorage.utils.SpringUtils;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.text.StringSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

@Component
@AllArgsConstructor
public class MQConfigurationSetter {

    private final static Logger logger = LoggerFactory.getLogger(MQConfigurationSetter.class);

    private final RabbitMQConfig config;
    private final SpringUtils springUtils;

    private HttpHeaders createHeaders(String username, String password) {
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(Charset.forName("US-ASCII")));
            String authHeader = "Basic " + new String(encodedAuth);
            set("Authorization", authHeader);
        }};
    }

    @PostConstruct
    public void setupMQ() {

        MutablePropertySources v0 = springUtils.getEnvironmentPropertySources();
        var theMap = springUtils.getPropertyMap();
        var props = springUtils.getSpringProperties();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = createHeaders(config.getImportDefinitionsUser(), config.getImportDefinitionsPassword());
        headers.setContentType(MediaType.APPLICATION_JSON);
        logger.info("Reading {}", config.getImportDefinitionsTemplatePath());
        String requestBody = "{}";
        InputStream istr = getClass().getClassLoader().getResourceAsStream(config.getImportDefinitionsTemplatePath());
        try {
            requestBody = new String(istr.readAllBytes(), "UTF-8");
        } catch (IOException e) {
            logger.error("ERROR reading import definitions template file", e);
        }

        String valuedBody = StringSubstitutor.replace(requestBody, springUtils.getSpringProperties());
        HttpEntity<String> requestEntity = new HttpEntity<>(valuedBody, headers);
        String postUrl = config.getImportDefinitionsUrl();
        logger.info("Posting MQ definitions to {}", postUrl);
        var responseBody =
                restTemplate.exchange(postUrl,
                        HttpMethod.POST,
                        requestEntity,
                        String.class);

        if (responseBody.getStatusCode().is2xxSuccessful()) {
            logger.info("SUCCESS: Posting MQ definitions to {}", postUrl);
        } else {
            logger.error("ERROR: Posting MQ definitions to {}", postUrl);
        }
    }


}
