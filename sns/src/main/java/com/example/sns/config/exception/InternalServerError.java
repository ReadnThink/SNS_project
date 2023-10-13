package com.example.sns.config.exception;

import org.springframework.http.HttpStatus;

public class InternalServerError extends CustomApiException {

    private static final String MESSAGE = "서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.";

    public InternalServerError() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

}
