package com.example.core.domain.post;

import com.example.core.domain.post.entity.Post;
import com.example.core.domain.post.entity.PostId;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Optional<Post> findById(PostId id);
    Post save(Post post);
    void delete(Post post);
}
