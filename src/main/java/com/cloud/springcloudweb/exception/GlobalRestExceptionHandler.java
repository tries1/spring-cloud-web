package com.cloud.springcloudweb.exception;

import com.cloud.springcloudweb.dto.ErrorResponseDto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalRestExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ErrorResponseDto exceptionHandle(Exception e){
        return ErrorResponseDto.builder()
                .message("오류가 발생하였습니다. : " + e.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();

    }

    @ExceptionHandler(ResponseStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponseDto responseStatusExceptionHandle(ResponseStatusException e){
        log.info("e.getStatus() : {}", e.getStatus());
        return ErrorResponseDto.builder()
                .message("ResponseStatusException. : " + e.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();

    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ErrorResponseDto userNotFoundExceptionHandle(UserNotFoundException e){
        return ErrorResponseDto.builder()
                .message(e.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build();

    }
}
