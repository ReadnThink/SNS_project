package com.example.sns_project.config.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginRespDto {

    private final Long id;
    private final String email;

    @Builder
    public LoginRespDto(final Long id, final String email) {
        this.id = id;
        this.email = email;
    }
}
