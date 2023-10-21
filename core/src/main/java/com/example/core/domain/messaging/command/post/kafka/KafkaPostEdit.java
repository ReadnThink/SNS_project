package com.example.core.domain.messaging.command.post.kafka;

import com.example.core.domain.messaging.event.Event;
import com.example.core.domain.post.entity.PostId;

public record KafkaPostEdit(PostId postId, String title, String content) implements Event {
}
