package com.example.sns.infra.jpa;

import com.example.core.domain.post.PostRepository;
import com.example.core.domain.post.entity.Post;
import com.example.core.domain.post.entity.PostId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class PostRepositoryImpl implements PostRepository {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public Optional<Post> findById(final PostId id) {
        try {
            return Optional.of(entityManager.find(Post.class, id));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public Post save(final Post post) {
        entityManager.persist(post);
        return post;
    }

    @Override
    public void delete(final Post post) {
        entityManager.remove(post);
    }
}
