package dev.vk.jfc.app.storage.appstorage.services;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
@AllArgsConstructor
public class S3StorageService {

    private final MinioClient minioClient;

    @SneakyThrows
    public void putObject(String s3Path, byte[] body)  {
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
