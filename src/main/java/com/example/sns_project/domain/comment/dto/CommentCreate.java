package com.example.sns_project.domain.comment.dto;

import com.example.sns_project.domain.comment.entity.Comment;
import com.example.sns_project.domain.post.entity.Post;
import com.example.sns_project.domain.user.entity.User;
import jakarta.validation.constraints.NotEmpty;

public record CommentCreate(@NotEmpty(message = "내용을 입력해주세요") String content) {
}
