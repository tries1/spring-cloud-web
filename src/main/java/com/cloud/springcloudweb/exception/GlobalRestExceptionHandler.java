package com.cloud.springcloudweb.exception;

import com.cloud.springcloudweb.dto.ErrorResponseDTO;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalRestExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ErrorResponseDTO exceptionHandle(Exception e){
        return ErrorResponseDTO.builder()
                .message("오류가 발생하였습니다. : " + e.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();

    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponseDTO bindExceptionHandle(BindException e){
        log.info("e.getBindingResult() : {}", e.getBindingResult());
        return ErrorResponseDTO.builder()
                .message("BindException. : " + e.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();

    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ErrorResponseDTO userNotFoundExceptionHandle(UserNotFoundException e){
        return ErrorResponseDTO.builder()
                .message(e.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build();

    }
}
