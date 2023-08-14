package com.example.sns_project.global.exception;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class CustomValidationException extends RuntimeException{
    private Map<String, String> errorMap;

    @Builder
    public CustomValidationException(String message, final Map<String, String> errorMap) {
        super(message);
        this.errorMap = errorMap;
    }
}
