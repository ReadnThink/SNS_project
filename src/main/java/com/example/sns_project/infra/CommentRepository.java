package com.example.sns_project.infra;

import com.example.sns_project.domain.comment.entity.Comment;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Optional<Comment> findById(Long id);
    Comment save(Comment comment);
    void deleteAll();

    List<Comment> findAll(final Pageable pageable);

    void delete(Comment comment);
}
