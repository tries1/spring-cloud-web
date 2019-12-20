package com.cloud.springcloudweb.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class UserDto {

    @Min(1)
    private Long id;

    @NotEmpty
    private String name;

    @Builder
    public UserDto(Long id, String name){
        this.id = id;
        this.name = name;
    }
}
