package com.adam.demo.services;

import com.adam.demo.models.RemoteFile;

import java.io.IOException;

public interface FileService {
    void saveFileToS3(RemoteFile file);
    RemoteFile readFileFromS3(String bucket, String key) throws IOException;
}
