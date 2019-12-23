package com.cloud.springcloudweb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDto {
    private String message;
    private Integer status;
}
