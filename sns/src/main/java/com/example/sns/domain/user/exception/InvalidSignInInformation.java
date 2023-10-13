package com.example.sns.domain.user.exception;

import com.example.sns.config.exception.CustomApiException;
import org.springframework.http.HttpStatus;

public class InvalidSignInInformation extends CustomApiException {

    private static final String MESSAGE = "아이디/비밀번호가 올바르지 않습니다.";

    public InvalidSignInInformation() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
