package com.example.query.infra.jpa;

import com.example.core.domain.comment.entity.Comment;
import com.example.core.domain.comment.entity.CommentId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentQueryRepositoryImpl extends JpaRepository<Comment, CommentId> {
}