package com.cloud.springcloudweb.dto;

import org.springframework.validation.annotation.Validated;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Validated
@NoArgsConstructor
public class UserDto {

    private Long id;

    private String name;

    @Builder
    public UserDto(Long id, String name){
        this.id = id;
        this.name = name;
    }
}
