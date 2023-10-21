package com.example.query.infra.jpa;

import com.example.core.domain.post.entity.Post;
import com.example.core.domain.post.entity.PostId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostQueryRepositoryImpl extends JpaRepository<Post, PostId> {
}
