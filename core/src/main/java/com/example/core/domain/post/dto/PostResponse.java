package com.example.core.domain.post.dto;

import com.example.core.domain.messaging.event.Event;
import com.example.core.domain.post.entity.Post;
import com.example.core.domain.post.entity.PostId;
import lombok.Builder;


public record PostResponse (PostId postId, String title, String content) implements Event {
    public PostResponse(Post post) {
        this(post.getPostId(), post.getTitle(), post.getContent());
    }

    @Builder
    public PostResponse(final PostId postId, final String title, final String content) {
        this.postId = postId;
        this.title = title;
        this.content = content;
    }
}
