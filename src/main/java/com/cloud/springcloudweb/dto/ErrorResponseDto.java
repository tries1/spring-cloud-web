package com.cloud.springcloudweb.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorResponseDto extends ResponseDto {

    @Builder
    public ErrorResponseDto(String message, Integer status){
        super(message, status);
    }
}
