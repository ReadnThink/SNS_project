package com.example.sns_project.domain.comment.dto;

import com.example.sns_project.domain.comment.entity.Comment;
import com.example.sns_project.domain.comment.entity.CommentId;
import lombok.Builder;

import java.time.LocalDateTime;

public record CommentResponse(CommentId id, String comment, String author, LocalDateTime lastModifiedAt) {

    public CommentResponse(Comment comment) {
        this(comment.getCommentId(), comment.getContent(), comment.getAuthor(), comment.getLastModifiedAt());
    }
    @Builder
    public CommentResponse(final CommentId id, final String comment, final String author, final LocalDateTime lastModifiedAt) {
        this.id = id;
        this.comment = comment;
        this.author = author;
        this.lastModifiedAt = lastModifiedAt;
    }
}
