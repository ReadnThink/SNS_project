package com.example.sns_project.domain.post.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostResponseDto {
    private final String title;
    private final String content;

    @Builder
    public PostResponseDto(final String title, final String content) {
        this.title = title;
        this.content = content;
    }
}
