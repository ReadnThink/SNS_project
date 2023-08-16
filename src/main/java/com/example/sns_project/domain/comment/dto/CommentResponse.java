package com.example.sns_project.domain.comment.dto;

import com.example.sns_project.domain.comment.entity.Comment;
import com.example.sns_project.domain.post.entity.Post;
import lombok.Builder;

public record CommentResponse(Long commentId, String comment, String author) {

    public CommentResponse(Comment comment) {
        this(comment.getId(), comment.getContent(), comment.getAuthor());
    }
    @Builder
    public CommentResponse(final Long commentId, final String comment, final String author) {
        this.commentId = commentId;
        this.comment = comment;
        this.author = author;
    }
}
