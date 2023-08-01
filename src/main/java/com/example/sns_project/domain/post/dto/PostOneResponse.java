package com.example.sns_project.domain.post.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostOneResponse {
    private final String title;
    private final String content;

    @Builder
    public PostOneResponse(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
