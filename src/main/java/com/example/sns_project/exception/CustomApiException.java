package com.example.sns_project.exception;

public abstract class CustomApiException extends RuntimeException{
    public CustomApiException(String message){
        super(message);
    }

    public abstract int statusCode();
}
