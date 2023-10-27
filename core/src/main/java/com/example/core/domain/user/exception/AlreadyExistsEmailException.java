package com.example.core.domain.user.exception;

import com.example.core.config.exception.CustomApiException;
import org.springframework.http.HttpStatus;

public class AlreadyExistsEmailException extends CustomApiException {
    private static final String MESSAGE = "이미 가입된 이메일입니다.";

    public AlreadyExistsEmailException() {
        super(MESSAGE);
    }


    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}