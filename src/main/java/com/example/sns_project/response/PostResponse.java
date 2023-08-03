package com.example.sns_project.response;

import com.example.sns_project.domain.post.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostResponse {
    private final String title;
    private final String content;

    public PostResponse(Post post) {
        this.title = post.getTitle();
        this.content = post.getContent();
    }
    @Builder
    public PostResponse(final String title, final String content) {
        this.title = title;
        this.content = content;
    }
}
