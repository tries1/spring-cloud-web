package com.cloud.springcloudweb.exception;

import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import java.util.stream.Collectors;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ValidationException extends RuntimeException {

    public ValidationException(Errors errors) {
        super(errors.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("\\n")));
    }
}
