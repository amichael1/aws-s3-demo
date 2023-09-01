package com.adam.demo.controllers

import com.adam.demo.models.RemoteFile
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.runner.RunWith
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.util.Assert
import software.amazon.awssdk.services.s3.S3Client
import spock.lang.Specification

@RunWith(SpringRunner.class)
@SpringBootTest
class S3ControllerTest extends Specification {

    private static final String TEST_STRING = "HelloTest"

    @SpringBean
    private S3Client s3Client = Mock(S3Client)

    @Autowired
    private S3Controller s3Controller

    @Autowired
    private ObjectMapper objectMapper

    def "PostFileToS3"() {
        given:
        RemoteFile remoteFile = RemoteFile.builder().content("TestContent").bucketName("TestBucket").key("TestKey").build()

        when:
        s3Controller.postFileToS3(remoteFile)

        then:
        1 * s3Client.putObject(_)
    }

    def "GetFileFromS3"() {
        when:
        s3Controller.getFileFromS3("testbucket", "testkey")

        then:
        1 * s3Client.getObjectAsBytes(_)

        Assert.isTrue(file.content().equals(TEST_STRING), "File content is equal to the test string")
    }
}
