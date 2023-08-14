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
        log.error(e.getMessage());
//        return new ResponseEntity<>(new ResponseDto<>(e.statusCode(),e.getMessage(),e.getValidation()), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(ResponseDto.builder()
                .code(e.statusCode())
                .message(e.getMessage())
                .validation(e.getValidation())
                .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BindingException.class)
    public ResponseEntity<?> validationApiException(BindingException e) {
        return new ResponseEntity<>(ResponseDto.builder()
                .code(-1)
                .message(e.getMessage())
                .data("")
                .build(), HttpStatus.BAD_REQUEST);
    }
}
