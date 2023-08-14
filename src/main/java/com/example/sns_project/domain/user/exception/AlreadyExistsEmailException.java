package com.example.sns_project.domain.user.exception;

import com.example.sns_project.global.exception.CustomApiException;

public class AlreadyExistsEmailException extends CustomApiException {
    private static final String MESSAGE = "이미 가입된 이메일입니다.";

    public AlreadyExistsEmailException() {
        super(MESSAGE);
    }

    public AlreadyExistsEmailException(String fieldName, String message) {
        super(MESSAGE);
        addValidation(fieldName, message);
    }

    @Override
    public int statusCode() {
        return 400;
    }
}