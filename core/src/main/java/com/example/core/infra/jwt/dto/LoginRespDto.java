package com.example.core.infra.jwt.dto;

import com.example.core.domain.user.entity.UserId;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginRespDto {

    private final UserId id;
    private final String email;

    @Builder
    public LoginRespDto(final UserId id, final String email) {
        this.id = id;
        this.email = email;
    }
}
