package com.example.sns_project.response;

import lombok.Getter;

@Getter
public class SessionResponse {
    private final String accessToken;

    public SessionResponse(final String accessToken) {
        this.accessToken = accessToken;
    }
}
