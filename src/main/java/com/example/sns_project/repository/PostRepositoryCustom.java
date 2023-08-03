package com.example.sns_project.repository;

import com.example.sns_project.domain.post.Post;
import com.example.sns_project.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);
}
