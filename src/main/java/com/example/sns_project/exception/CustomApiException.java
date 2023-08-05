package com.example.sns_project.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class CustomApiException extends RuntimeException{

    private final Map<String, String> validation = new HashMap<>();

    public CustomApiException(String message){
        super(message);
    }

    public abstract int statusCode();

    public void addValidation(String fieldName, String message) {
        validation.put(fieldName, message);
    }
}
