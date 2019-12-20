package com.cloud.springcloudweb.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super(String.format("ID(%d) 사용자를 찾을수 없습니다.", id));
    }
}
