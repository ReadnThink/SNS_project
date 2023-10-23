package com.example.core.domain.messaging.command.post.kafka;

import com.example.core.domain.messaging.event.Event;
import com.example.core.domain.post.entity.Post;
import com.example.core.domain.post.entity.PostId;

public record KafkaPostDelete(PostId postId, String title, String content) implements Event {

    public Post toEntity() {
        return Post.builder()
                .postId(postId)
                .title(title)
                .content(content)
                .build();
    }
}
