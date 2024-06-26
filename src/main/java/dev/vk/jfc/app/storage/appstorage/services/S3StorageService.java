package dev.vk.jfc.app.storage.appstorage.services;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Service
@AllArgsConstructor
public class S3StorageService implements StorageService {

    private final MinioClient minioClient;

    @SneakyThrows
    @Override
    public void putObject(String s3Path, byte[] body) {
        long partSize = 5 * 1024 * 1024;
        minioClient.putObject(PutObjectArgs
                .builder()
                .bucket("jpgdata")
                .contentType("image/jpeg")
                .object(s3Path)
                .stream(new ByteArrayInputStream(body), body.length, partSize)
                .build());
    }

    @SneakyThrows
    @Override
    public byte[] getObject(String s3Path) {
        return minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket("jpgdata")
                        .object(s3Path)
                        .build()
        ).readAllBytes();
    }
}
