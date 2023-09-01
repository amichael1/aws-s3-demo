package com.adam.demo.services;

import com.adam.demo.models.RemoteFile;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
@SpringBootTest
public class S3FileServiceT {
    private static final String TEST_STRING = "HelloTest";

    @MockBean
    private S3Client s3Client;

    @Autowired
    private S3FileService s3FileService;


    @Test
    void saveFileToPath() {
        PutObjectResponse objectResponse = PutObjectResponse.builder().build();
        Mockito.when(s3Client.putObject(Mockito.any(PutObjectRequest.class), Mockito.any(RequestBody.class))).thenReturn(objectResponse);

        RemoteFile remoteFile = RemoteFile.builder().key("Test/File").content(TEST_STRING).bucketName("TestBucket").build();
        s3FileService.saveFileToS3(remoteFile);

        Mockito.verify(s3Client, times(1))
                .putObject(Mockito.any(PutObjectRequest.class), Mockito.any(RequestBody.class));
    }

    @Test
    void findFileByPath() {
        GetObjectResponse objectResponse = GetObjectResponse.builder().build();
        ResponseBytes<GetObjectResponse> testResponse = ResponseBytes.fromByteArray(objectResponse, TEST_STRING.getBytes());
        Mockito.when(s3Client.getObjectAsBytes(Mockito.any(GetObjectRequest.class))).thenReturn(testResponse);

        RemoteFile file = s3FileService.readFileFromS3("TestBucket", "Test/File");

        Mockito.verify(s3Client, times(1))
                .getObjectAsBytes(Mockito.any(GetObjectRequest.class));

        Assert.isTrue(file.content().equals(TEST_STRING), "File content is equal to the test string");
    }
}
