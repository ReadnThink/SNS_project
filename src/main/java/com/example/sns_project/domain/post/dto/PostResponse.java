package com.example.sns_project.domain.post.dto;

import com.example.sns_project.domain.post.entity.Post;
import lombok.Builder;


public record PostResponse (Long id, String title, String content){

    public PostResponse(Post post) {
        this(post.getId(), post.getTitle(), post.getContent());
    }

    @Builder
    public PostResponse(final Long id, final String title, final String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}
