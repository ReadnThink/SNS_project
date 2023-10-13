package com.example.sns.domain.post;

import com.example.sns.domain.post.entity.Post;
import com.example.sns.domain.post.entity.PostId;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Optional<Post> findById(PostId id);
    Post save(Post post);
    void deleteAll();

    List<Post> findAll(final Pageable pageable);

    void delete(Post post);
}
