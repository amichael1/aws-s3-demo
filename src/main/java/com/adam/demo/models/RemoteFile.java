package com.adam.demo.models;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RemoteFile {
    private String bucketName;
    private String key;
    private String content;
}