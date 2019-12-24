package com.cloud.springcloudweb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import java.util.stream.Collectors;

public class NoMappingHandlerException extends GlobalException {

    private final static HttpStatus status = HttpStatus.NOT_FOUND;

    public NoMappingHandlerException() {
        super(status, "등록된 URL을 찾을수 없습니다.");
    }
}
