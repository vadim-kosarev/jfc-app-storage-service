package dev.vk.jfc.app.storage.appstorage.mq;

import dev.vk.jfc.app.storage.appstorage.services.ImageDataStorageService;
import dev.vk.jfc.app.storage.appstorage.services.S3StorageService;
import dev.vk.jfc.app.storage.appstorage.services.StorageService;
import dev.vk.jfc.jfccommon.Jfc;
import jakarta.transaction.NotSupportedException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
@AllArgsConstructor
public class ProcessedImageReceiver {

    private final static Logger logger = LoggerFactory.getLogger(ProcessedImageReceiver.class);

    private static final Set<String> imageMessageTypes = Set.of(
            Jfc.TP_PROCESSED_FRAME, Jfc.TP_PROCESSED_FRAME_FACES, Jfc.TP_PROCESSED_FRAME_FACE
    );

    @Autowired
    private final ImageDataStorageService imageDataStorageService;

    @Autowired
    private final StorageService storageService;

    @RabbitListener(queues = "q-indexed-images")
    @SneakyThrows
    public void process(Message message) {

        Map<String, Object> headers = message.getMessageProperties().getHeaders();
        String s3Path = (String) headers.get(Jfc.K_S3PATH);
        String messageType = (String) headers.get(Jfc.K_MESSAGE_TYPE);

        logger.info("uuid: {} - {} / {}",
                headers.get(Jfc.K_UUID),
                messageType,
                s3Path
        );

        if (!imageMessageTypes.contains(messageType)) return;

        byte[] payload = message.getBody();
        storageService.putObject(s3Path, payload);

        switch (messageType) {
            case Jfc.TP_PROCESSED_FRAME:
                imageDataStorageService.onFrameImage(headers, payload);
                break;
            case Jfc.TP_PROCESSED_FRAME_FACES:
            case Jfc.TP_PROCESSED_FRAME_FACE:
                imageDataStorageService.onFrameFacesImage(headers, payload);
                break;
            default:
                throw new NotSupportedException("Not supported: " + messageType);
        }
    }
}