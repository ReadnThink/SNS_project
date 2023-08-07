package com.example.sns_project.config.data;

import lombok.Getter;

@Getter
public class UserSession {
    private final long id;

    public UserSession(final long id) {
        this.id = id;
    }
}
