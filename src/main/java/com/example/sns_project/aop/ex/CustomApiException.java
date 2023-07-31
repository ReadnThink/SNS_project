package com.example.sns_project.aop.ex;

public class CustomApiException extends RuntimeException{
    public CustomApiException(String message){
        super(message);
    }
}
