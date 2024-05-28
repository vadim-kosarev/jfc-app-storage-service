package dev.vk.jfc.app.storage.appstorage.mq;

import jakarta.annotation.PostConstruct;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

@Component
public class MQConfigurationSetter {

    private final static Logger logger = LoggerFactory.getLogger(MQConfigurationSetter.class);

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
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = createHeaders("root", "Password123!");
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = "{}";

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        String postUrl = "http://localhost:15672/api/definitions";

        logger.info("Posting MQ definitions to {}", postUrl);

        var responseBody =
                restTemplate.exchange(postUrl,
                        HttpMethod.POST,
                        requestEntity,
                        String.class);

        if(responseBody.getStatusCode().is2xxSuccessful()) {
            logger.info("SUCCESS: Posting MQ definitions to {}", postUrl);
        } else {
            logger.error("ERROR: Posting MQ definitions to {}", postUrl);
        }
    }


}
