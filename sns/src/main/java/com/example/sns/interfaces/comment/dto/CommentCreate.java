package com.example.sns.interfaces.comment.dto;

import com.example.sns.domain.messaging.command.Command;
import jakarta.validation.constraints.NotEmpty;

public record CommentCreate(@NotEmpty(message = "내용을 입력해주세요") String content) implements Command {
}
