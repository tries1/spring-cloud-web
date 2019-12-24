package com.cloud.springcloudweb.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDto {
    private String message;
    private Integer status;
}
