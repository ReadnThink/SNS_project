package com.example.sns_project.infra;

import com.example.sns_project.domain.post.PostRepository;
import com.example.sns_project.domain.post.entity.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PostRepositoryImpl implements PostRepository {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public Optional<Post> findById(final Long id) {
        try {
            return Optional.of(entityManager.find(Post.class, id));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Post save(final Post post) {
        entityManager.persist(post);
        return post;
    }

    @Override
    public void deleteAll() {
        entityManager.createQuery("delete from Post");
    }

    @Override
    public List<Post> findAll(final Pageable pageable) {
        // todo Pageable 처리 해야함...
        return entityManager.createQuery("select p from Post p")
                .setFirstResult(0)
                .setMaxResults(10)
                .getResultList();
    }

    @Override
    public void delete(final Post post) {
        entityManager.remove(post);
    }
}
