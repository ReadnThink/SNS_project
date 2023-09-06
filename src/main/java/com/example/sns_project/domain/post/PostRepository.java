package com.example.sns_project.domain.post;

import com.example.sns_project.domain.comment.entity.Comment;
import com.example.sns_project.domain.post.entity.Post;
import com.example.sns_project.domain.post.entity.PostId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Optional<Post> findById(PostId id);
    Post save(Post post);
    void deleteAll();

    List<Post> findAll(final Pageable pageable);

    void delete(Post post);
}
