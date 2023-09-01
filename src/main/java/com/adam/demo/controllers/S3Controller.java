package com.adam.demo.controllers;

import com.adam.demo.models.RemoteFile;
import com.adam.demo.services.FileService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController("/aws/s3")
@RequiredArgsConstructor
public class S3Controller {
    private static final Logger logger = LoggerFactory.getLogger(S3Controller.class);
    private static final String fileUrl = "https://{0}.s3.us-west-2.amazonaws.com/{1}";
    private final FileService fileService;

    @PostMapping("/file")
    public ResponseEntity<RemoteFile> postFileToS3(@RequestBody RemoteFile file) throws URISyntaxException {
        logger.info("Received request to save file with info: " + file);
        fileService.saveFileToS3(file);

        return ResponseEntity
                .created(new URI(String.format(fileUrl, file.bucketName(), file.key())))
                .body(file);
    }


    @GetMapping("/file")
    public ResponseEntity<RemoteFile> getFileFromS3(@RequestParam("bucketName") String bucketName,
                                    @RequestParam("keyName") String keyName) throws Exception{
        logger.info("Received request to get file with keyName: " + keyName);

        return ResponseEntity.ok(fileService.readFileFromS3(bucketName, keyName));
    }


}
