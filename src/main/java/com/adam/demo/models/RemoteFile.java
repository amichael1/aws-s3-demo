package com.adam.demo.models;

import lombok.Builder;
@Builder
public record RemoteFile(String bucketName, String key, String content){ }
