package com.example.sns_project.controller;

import com.example.sns_project.config.AppConfig;
import com.example.sns_project.request.SignUp;
import com.example.sns_project.response.ResponseDto;
import com.example.sns_project.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

/**
 * commit : 인증하기 위한 AuthController 생성
 */

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AppConfig appConfig;

    @PostMapping("/auth/signup")
    public ResponseEntity<ResponseDto<Object>> signUp(@RequestBody SignUp signUp) {
        authService.signUp(signUp);

        return new ResponseEntity<>(ResponseDto.builder()
                .code(1)
                .message("회원가입을 성공했습니다.")
                .build(), OK);
    }

}
