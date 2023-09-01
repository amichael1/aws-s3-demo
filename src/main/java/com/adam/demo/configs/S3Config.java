package com.adam.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.*;
import software.amazon.awssdk.http.apache.ApacheHttpClient;


@Configuration
public class S3Config {

    @Bean
    public S3Client getS3Client(){
        ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.create();
        Region region = Region.US_EAST_1;

        return S3Client.builder()
                .credentialsProvider(credentialsProvider)
                .region(region)
                .httpClientBuilder(ApacheHttpClient.builder())
                .build();
    }
}
