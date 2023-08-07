package com.example.sns_project.controller;

import com.example.sns_project.config.AppConfig;
import com.example.sns_project.request.Login;
import com.example.sns_project.request.SignUp;
import com.example.sns_project.response.ResponseDto;
import com.example.sns_project.response.SessionResponse;
import com.example.sns_project.service.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.util.Date;

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

    @PostMapping("/auth/login")
    public ResponseEntity<ResponseDto<Object>> login(@RequestBody Login login) {
        final Long userId = authService.login(login);

        SecretKey key = Keys.hmacShaKeyFor(appConfig.getJwtKey());

        final String jwt = Jwts.builder()
                .setSubject(String.valueOf(userId))
                .signWith(key)
                .setIssuedAt(new Date())
                .compact();

        return new ResponseEntity<>(ResponseDto.builder()
                .code(1)
                .message("로그인을 성공했습니다.")
                .data(new SessionResponse(jwt))
                .build(), OK);
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<ResponseDto<Object>> signUp(@RequestBody SignUp signUp) {
        authService.signUp(signUp);

        return new ResponseEntity<>(ResponseDto.builder()
                .code(1)
                .message("회원가입을 성공했습니다.")
                .build(), OK);
    }
}
