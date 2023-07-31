package com.example.sns_project.service;

import com.example.sns_project.domain.post.Post;
import com.example.sns_project.domain.post.dto.PostCreate;
import com.example.sns_project.domain.post.dto.PostResponseDto;
import com.example.sns_project.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    PostRepository postRepository;

    @Test
    @DisplayName("글 작성 성공")
    void test1() {

        final Post post = Post.builder()
                .id(1L)
                .title("제목")
                .content("내용")
                .build();
        given(postRepository.save(any())).willReturn(post);

        final PostResponseDto responseDto = postService.write(PostCreate.builder()
                .title("제목")
                .content("내용")
                .build());

        assertThat(responseDto.getTitle()).isEqualTo("제목");
        assertThat(responseDto.getContent()).isEqualTo("내용");
    }
}