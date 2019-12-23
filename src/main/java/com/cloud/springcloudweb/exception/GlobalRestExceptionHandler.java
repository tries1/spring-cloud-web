package com.cloud.springcloudweb.exception;

import com.cloud.springcloudweb.dto.ErrorResponseDto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponseDto responseStatusExceptionHandle(ValidationException e){
        return ErrorResponseDto.builder()
                .message("입력값을 확인해주세요. : " + e.getMessage())
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
