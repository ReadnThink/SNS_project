package com.example.core.domain.messaging.command.comment;

import com.example.core.domain.comment.dto.CommentCreate;
import com.example.core.domain.messaging.command.Command;
import com.example.core.domain.post.entity.PostId;
import com.example.core.domain.user.entity.UserId;

public record CommentCreateMessage(CommentCreate commentCreate, UserId userId, PostId postId) implements Command {
}
