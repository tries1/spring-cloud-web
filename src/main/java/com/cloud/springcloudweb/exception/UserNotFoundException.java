package com.cloud.springcloudweb.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends GlobalException {

    private final static HttpStatus status = HttpStatus.NOT_FOUND;

    public UserNotFoundException(Long id) {
        super(status, String.format("ID(%d) 사용자를 찾을수 없습니다.", id));
    }
}
