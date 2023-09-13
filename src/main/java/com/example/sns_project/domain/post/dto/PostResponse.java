package com.example.sns_project.domain.post.dto;

import com.example.sns_project.domain.post.entity.Post;
import com.example.sns_project.domain.post.entity.PostId;
import lombok.Builder;


public record PostResponse (PostId id, String title, String content){
    public PostResponse(Post post) {
        this(post.getPostId(), post.getTitle(), post.getContent());
    }

    @Builder
    public PostResponse(final PostId id, final String title, final String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}
