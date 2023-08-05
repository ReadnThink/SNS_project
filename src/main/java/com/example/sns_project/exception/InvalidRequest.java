package com.example.sns_project.exception;

public class InvalidRequest extends CustomApiException{

    private static final String MESSAGE = "잘못된 요청입니다.";

    public InvalidRequest() {
        super(MESSAGE);
    }

    @Override
    public int statusCode() {
        return 400;
    }
}
