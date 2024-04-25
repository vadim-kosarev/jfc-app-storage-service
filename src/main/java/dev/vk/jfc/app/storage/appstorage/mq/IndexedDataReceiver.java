package dev.vk.jfc.app.storage.appstorage.mq;

import dev.vk.jfc.app.storage.appstorage.services.ImageDataStorageService;
import dev.vk.jfc.jfccommon.Jfc;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
@AllArgsConstructor
public class IndexedDataReceiver {

    private final static Logger logger = LoggerFactory.getLogger(IndexedDataReceiver.class);

    private final ImageDataStorageService imageDataStorageService;

    @RabbitListener(queues = "q-indexed-data")
    @SneakyThrows
    public void onIndexedDataMessage(Message message) {

        Map<String, Object> headers = message.getMessageProperties().getHeaders();
        String messageType = (String) headers.get(Jfc.K_MESSAGE_TYPE);
        UUID msgUuid = UUID.fromString((String) headers.get(Jfc.K_UUID));
        UUID msgParentUuid = UUID.fromString((String) headers.get(Jfc.K_PARENT_UUID));

        logger.info("{}: uuid: {}, parent: {}", messageType, msgUuid, msgParentUuid);

        imageDataStorageService.onIndexedData(message);

    }

}
