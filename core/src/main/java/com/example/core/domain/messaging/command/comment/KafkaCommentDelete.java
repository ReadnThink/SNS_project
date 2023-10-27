package com.example.core.domain.messaging.command.comment;

import com.example.core.domain.comment.entity.CommentId;
import com.example.core.domain.messaging.command.Command;
import com.example.core.domain.user.entity.UserId;

public record KafkaCommentDelete(CommentId commentId, UserId userId) implements Command {
}
