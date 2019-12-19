package com.cloud.springcloudweb.exception;

import com.cloud.springcloudweb.dto.ErrorResponseDTO;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ErrorResponseDTO handleException(Exception e){
        return ErrorResponseDTO.builder()
                .message("에러발생! : " + e.getMessage())
                .status(500)
                .build();

    }

}
