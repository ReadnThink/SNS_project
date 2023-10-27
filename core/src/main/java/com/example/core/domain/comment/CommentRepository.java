package com.example.core.domain.comment;

import com.example.core.domain.comment.entity.Comment;
import com.example.core.domain.comment.entity.CommentId;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Optional<Comment> findById(CommentId id);
    Comment save(Comment comment);
    void deleteAll();

    List<Comment> findAll(final Pageable pageable);

    void delete(Comment comment);
}
