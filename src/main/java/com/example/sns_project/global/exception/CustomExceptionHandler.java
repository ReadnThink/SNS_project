package com.example.sns_project.global.exception;

import com.example.sns_project.global.util.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<?> apiException(CustomApiException e){
        return new ResponseEntity<>(ResponseDto.builder()
                .message(e.getMessage())
                .build(), e.getStatus());
    }

    @ExceptionHandler(BindingException.class)
    public ResponseEntity<?> validationApiException(BindingException e) {
        return new ResponseEntity<>(ResponseDto.builder()
                .message(e.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }
}
