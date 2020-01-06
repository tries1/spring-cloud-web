package com.cloud.springcloudweb.exception;

import org.springframework.http.HttpStatus;

public class FileHandleException extends GlobalException {

    public FileHandleException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, String.format("파일 처리중 문제가 발생하였습니다."));
    }

    public FileHandleException(String filename) {
        super(HttpStatus.NOT_FOUND, String.format("파일을 찾을수 없습니다."));
    }
}
