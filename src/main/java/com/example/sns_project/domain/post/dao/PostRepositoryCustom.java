package com.example.sns_project.domain.post.dao;

import com.example.sns_project.domain.post.entity.Post;
import com.example.sns_project.domain.post.dto.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);
}
