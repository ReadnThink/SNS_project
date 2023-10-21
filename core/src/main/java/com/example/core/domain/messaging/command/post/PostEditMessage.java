package com.example.core.domain.messaging.command.post;

import com.example.core.domain.messaging.command.Command;
import com.example.core.domain.post.dto.PostEdit;
import com.example.core.domain.post.entity.PostId;
import com.example.core.domain.user.entity.UserId;

public record PostEditMessage(PostEdit postEdit, PostId postId, UserId userId) implements Command {
}
