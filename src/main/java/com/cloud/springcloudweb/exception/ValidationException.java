package com.cloud.springcloudweb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import java.util.stream.Collectors;

public class ValidationException extends GlobalException {

    private final static HttpStatus status = HttpStatus.BAD_REQUEST;

    public ValidationException(Errors errors) {
        super(status, errors.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("\\n")));
    }
}
