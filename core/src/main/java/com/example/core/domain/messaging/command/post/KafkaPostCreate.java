package com.example.core.domain.messaging.command.post;

import com.example.core.domain.messaging.command.Command;
import com.example.core.domain.post.dto.PostCreate;
import com.example.core.domain.user.entity.UserId;

public record KafkaPostCreate(PostCreate postCreate, UserId userId) implements Command {
}
