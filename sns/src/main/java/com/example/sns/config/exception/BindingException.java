package com.example.sns.config.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BindingException extends RuntimeException{
    private String message;
}
