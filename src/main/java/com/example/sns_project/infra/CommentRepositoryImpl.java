package com.example.sns_project.infra;

import com.example.sns_project.domain.comment.CommentRepository;
import com.example.sns_project.domain.comment.entity.Comment;
import com.example.sns_project.domain.comment.entity.CommentId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

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
    public Comment save(final Comment comment) {
        entityManager.persist(comment);
        return comment;
    }

    @Override
    public void deleteAll() {
        entityManager.createQuery("delete from Comment");
    }

    @Override
    public void delete(final Comment comment) {
        entityManager.remove(comment);
    }

    @Override
    public List<Comment> findAll(final Pageable pageable) {
        // todo Pageable 처리 해야함...
        return entityManager.createQuery("select c from Comment c")
                .setFirstResult(0)
                .setMaxResults(10)
                .getResultList();
    }
}
