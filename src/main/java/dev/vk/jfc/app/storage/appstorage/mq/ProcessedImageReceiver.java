package dev.vk.jfc.app.storage.appstorage.mq;

import dev.vk.jfc.app.storage.appstorage.services.ImageDataStorageService;
import dev.vk.jfc.app.storage.appstorage.services.S3StorageService;
import dev.vk.jfc.jfccommon.Jfc;
import jakarta.transaction.NotSupportedException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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

    private final ImageDataStorageService imageDataStorageService;
    private final S3StorageService s3StorageService;

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

        byte[] body = message.getBody();
        s3StorageService.putObject(s3Path, body);

        switch (messageType) {
            case Jfc.TP_PROCESSED_FRAME:
                imageDataStorageService.onProcessedImage(message);
                break;
            case Jfc.TP_PROCESSED_FRAME_FACES:
            case Jfc.TP_PROCESSED_FRAME_FACE:
                imageDataStorageService.onFrameFacesImage(message);
                break;
            default:
                throw new NotSupportedException("Not supported: " + messageType);
        }

    }
}
