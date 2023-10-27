package com.example.core.domain.messaging.command.comment;

import com.example.core.domain.comment.dto.CommentEdit;
import com.example.core.domain.comment.entity.CommentId;
import com.example.core.domain.messaging.command.Command;
import com.example.core.domain.user.entity.UserId;

public record KafkaCommentEdit(CommentId commentId, CommentEdit commentEdit, UserId userId) implements Command {
}
