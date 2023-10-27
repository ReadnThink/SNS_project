package com.example.core.domain.messaging.command.post.kafka;

import com.example.core.domain.messaging.event.Event;
import com.example.core.domain.post.entity.Post;
import com.example.core.domain.post.entity.PostId;
import com.example.core.domain.user.entity.UserId;

public record KafkaPostCreate(PostId postId, UserId userId,  String title, String content) implements Event {

    public Post toEntity() {
        return Post.builder()
                .postId(postId)
                .userId(userId)
                .title(title)
                .content(content)
                .build();
    }
}
