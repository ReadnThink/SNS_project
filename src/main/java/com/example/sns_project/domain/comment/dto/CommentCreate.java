package com.example.sns_project.domain.comment.dto;

import com.example.sns_project.config.messaging.command.Command;
import jakarta.validation.constraints.NotEmpty;

public record CommentCreate(@NotEmpty(message = "내용을 입력해주세요") String content) implements Command {
}
