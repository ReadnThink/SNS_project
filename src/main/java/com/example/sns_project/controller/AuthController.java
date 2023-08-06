package com.example.sns_project.controller;

import com.example.sns_project.config.AppConfig;
import com.example.sns_project.request.Login;
import com.example.sns_project.response.SessionResponse;
import com.example.sns_project.service.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

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
    public SessionResponse login(@RequestBody Login login) {
        final Long userId = authService.signIn(login);

        SecretKey key = Keys.hmacShaKeyFor(appConfig.getJwtKey());

        final String jwt = Jwts.builder()
                .setSubject(String.valueOf(userId))
                .signWith(key)
                .setIssuedAt(new Date())
                .compact();

        return new SessionResponse(jwt);
    }
}
