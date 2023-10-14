package com.example.core.interfaces.comment.dto;

import com.example.core.domain.messaging.command.Command;
import jakarta.validation.constraints.NotEmpty;

public record CommentCreate(@NotEmpty(message = "내용을 입력해주세요") String content) implements Command {
}
