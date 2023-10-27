package com.example.sns.infra.jpa;

import com.example.core.domain.comment.CommentRepository;
import com.example.core.domain.comment.entity.Comment;
import com.example.core.domain.comment.entity.CommentId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class CommentRepositoryImpl implements CommentRepository {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public Optional<Comment> findById(final CommentId id) {
        try {
            return Optional.of(entityManager.find(Comment.class, id));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public Comment save(final Comment comment) {
        entityManager.persist(comment);
        return comment;
    }

    @Override
    public void delete(final Comment comment) {
        entityManager.remove(comment);
    }
}
