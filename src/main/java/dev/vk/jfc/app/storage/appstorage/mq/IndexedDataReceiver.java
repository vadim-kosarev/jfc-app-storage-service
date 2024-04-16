package dev.vk.jfc.app.storage.appstorage.mq;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;

@Component
@AllArgsConstructor
public class IndexedDataReceiver {

    private final static Logger logger = LoggerFactory.getLogger(IndexedDataReceiver.class);

    private final MinioClient minioClient;

    @RabbitListener(queues = "q-indexed-images")
    @SneakyThrows
    public void process(Message message) {
        String s3Path = String.valueOf(message.getMessageProperties().getHeaders().get("s3Path"));
        String messageType = String.valueOf(message.getMessageProperties().getHeaders().get("message-type"));
        logger.info("uuid: {} - {} / {}",
                message.getMessageProperties().getHeaders().get("uuid"),
                messageType,
                s3Path
        );
        if (
                !"processed-frame-faces".equals(messageType)
                        && !"processed-frame-face".equals(messageType)
                        && !"processed-frame".equals(messageType)
        ) return;
        byte[] body = message.getBody();
        long partSize = 5 * 1024 * 1024;
        minioClient.putObject(PutObjectArgs
                .builder()
                .bucket("jpgdata")
                .contentType("image/jpeg")
                .object(s3Path)
                .stream(new ByteArrayInputStream(body), body.length, partSize)
                .build());
    }

}
