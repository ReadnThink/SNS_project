package com.example.sns_project.domain.comment.dto;

import lombok.Builder;

@Builder
public
record CommentResponse(Long commentId, String comment, String author) {
}
