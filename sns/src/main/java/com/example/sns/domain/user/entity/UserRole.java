package com.example.sns.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserRole {
    ADMIN("관리자"),
    USER("유저");

    private String value;
}
