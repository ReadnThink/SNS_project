package com.example.sns_project.interfaces.comment.dto;

import com.example.sns_project.domain.messaging.command.Command;
import jakarta.validation.constraints.NotEmpty;

public record CommentCreate(@NotEmpty(message = "내용을 입력해주세요") String content) implements Command {
}
