package com.example.sns_project.config.exception;

import com.example.sns_project.infra.util.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {
    private static final String SERVER_ERROR = "서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.";
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<Object>> allException(Exception e){
        return new ResponseEntity<>(ResponseDto.builder()
                .message(SERVER_ERROR)
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<ResponseDto<Object>> apiException(CustomApiException e){
        return new ResponseEntity<>(ResponseDto.builder()
                .message(e.getMessage())
                .build(), e.getStatus());
    }

    @ExceptionHandler(BindingException.class)
    public ResponseEntity<ResponseDto<Object>> validationApiException(BindingException e) {
        return new ResponseEntity<>(ResponseDto.builder()
                .message(e.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }
}
