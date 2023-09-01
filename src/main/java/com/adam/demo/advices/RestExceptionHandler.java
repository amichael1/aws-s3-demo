package com.adam.demo.advices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);
    @ExceptionHandler({Exception.class})
    protected ResponseEntity<Object> handleExceptionScenario(Exception ex, WebRequest request) {
        logger.error("Service Failed to Handle Request", ex);

        return handleExceptionInternal(ex, "Service Failed to Handle Request",
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler({NoSuchBucketException.class, NoSuchKeyException.class})
    protected ResponseEntity<Object> handleRemoteFileNotFoundExceptionScenario(Exception ex, WebRequest request) {
        logger.error("Could not find remote file", ex);

        return handleExceptionInternal(ex, "Could not find remote file",
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}