package com.adam.demo.services;

import com.adam.demo.models.RemoteFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.*;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor()
public class S3FileService implements FileService {

    private final S3Client s3Client;

    @Override
    public void saveFileToS3(RemoteFile file) {
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(file.getBucketName())
                .key(file.getKey())
                .build();


        s3Client.putObject(objectRequest, RequestBody.fromByteBuffer(ByteBuffer.wrap(file.getContent().getBytes())));
    }

    @Override
    public RemoteFile readFileFromS3(String bucket, String key) {
        GetObjectRequest objectRequest = GetObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        String content = s3Client.getObjectAsBytes(objectRequest).asString(StandardCharsets.UTF_8);

        return RemoteFile.builder()
                .content(content)
                .bucketName(bucket)
                .key(key)
                .build();

    }
}
