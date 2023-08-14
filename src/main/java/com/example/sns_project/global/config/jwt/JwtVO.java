package com.example.sns_project.global.config.jwt;

public interface JwtVO {

    public static final int EXPIRATION_TIM = 1000 * 60 * 60 * 24 * 7;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER = "Authorization";
}
