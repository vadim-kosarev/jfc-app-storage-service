package dev.vk.jfc.app.storage.appstorage.mq;

import dev.vk.jfc.app.storage.appstorage.services.ImageDataStorageService;
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
public class IndexedDataReceiver {

    private final static Logger logger = LoggerFactory.getLogger(IndexedDataReceiver.class);


    private static final Set<String> imageMessageTypes = Set.of(
            Jfc.TP_PROCESSED_FRAME, Jfc.TP_PROCESSED_FRAME_FACES, Jfc.TP_PROCESSED_FRAME_FACE
    );
    //private final MinioClient minioClient;

    private final ImageDataStorageService imageDataStorageService;

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

//        byte[] body = message.getBody();
//        long partSize = 5 * 1024 * 1024;
        /*
        minioClient.putObject(PutObjectArgs
                .builder()
                .bucket("jpgdata")
                .contentType("image/jpeg")
                .object(s3Path)
                .stream(new ByteArrayInputStream(body), body.length, partSize)
                .build());
         */

        if (Jfc.TP_PROCESSED_FRAME.equals(messageType)) {
            imageDataStorageService.onProcessedImage(message);
        } else if (Jfc.TP_PROCESSED_FRAME_FACES.equals(messageType)) {
            imageDataStorageService.onImageData(message);
        } else if (Jfc.TP_PROCESSED_FRAME_FACE.equals(messageType)) {
            imageDataStorageService.onImageDataItem(message);
        } else {
            throw new NotSupportedException("Not supported: " + messageType);
        }
    }


}
