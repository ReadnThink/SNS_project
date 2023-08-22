package com.example.sns_project.config.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class UnAuthorized extends CustomApiException {

    private static final String MESSAGE = "인증이 필요합니다.";

    public UnAuthorized() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getStatus() {
        return UNAUTHORIZED;
    }
}
