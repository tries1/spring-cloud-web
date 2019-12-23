package com.cloud.springcloudweb.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /*@ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ErrorResponseDto handleException(Exception e){
        return ErrorResponseDto.builder()
                .message("에러발생! : " + e.getMessage())
                .status(500)
                .build();

    }*/

}
