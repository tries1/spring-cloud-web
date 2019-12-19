package com.cloud.springcloudweb.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponseDTO {
    private String message;
    private Integer status;
}
