package com.example.sns.domain.comment.dto;

import com.example.sns.domain.comment.entity.Comment;
import com.example.sns.domain.comment.entity.CommentId;
import com.example.sns.domain.messaging.event.Event;
import lombok.Builder;

import java.time.LocalDateTime;

public record CommentResponse(CommentId commentId, String comment, String author, LocalDateTime lastModifiedAt) implements Event {

    public CommentResponse(Comment comment) {
        this(comment.getCommentId(), comment.getContent(), comment.getAuthor(), comment.getLastModifiedAt());
    }
    @Builder
    public CommentResponse(final CommentId commentId, final String comment, final String author, final LocalDateTime lastModifiedAt) {
        this.commentId = commentId;
        this.comment = comment;
        this.author = author;
        this.lastModifiedAt = lastModifiedAt;
    }
}
