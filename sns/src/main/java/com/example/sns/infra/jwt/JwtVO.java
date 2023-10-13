package com.example.sns.infra.jwt;

public class JwtVO {
    public static final int EXPIRATION_TIM = 1000 * 60 * 60 * 24 * 7;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER = "Authorization";
}
