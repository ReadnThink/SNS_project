package com.example.sns_project.domain.user.exception;

import com.example.sns_project.global.exception.CustomApiException;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

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
