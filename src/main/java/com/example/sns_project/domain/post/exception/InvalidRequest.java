package com.example.sns_project.domain.post.exception;

import com.example.sns_project.global.exception.CustomApiException;
import org.springframework.http.HttpStatus;

public class InvalidRequest extends CustomApiException {

        private static final String MESSAGE = "해당 단어는 포함될 수 없습니다.";

    public InvalidRequest() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
