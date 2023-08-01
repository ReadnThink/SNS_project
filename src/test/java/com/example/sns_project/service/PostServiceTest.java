package com.example.sns_project.service;

import com.example.sns_project.domain.post.Post;
import com.example.sns_project.domain.post.dto.PostCreate;
import com.example.sns_project.domain.post.dto.PostResponse;
import com.example.sns_project.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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

        final PostResponse responseDto = postService.write(PostCreate.builder()
                .title("제목")
                .content("내용")
                .build());

        assertThat(responseDto.getTitle()).isEqualTo("제목");
        assertThat(responseDto.getContent()).isEqualTo("내용");
    }

    @Test
    @DisplayName("글 1개 조회")
    void test() {
        //given
        final Post response = Post.builder()
                .id(1L)
                .title("Title")
                .content("Content")
                .build();
        final PostResponse oneResponse = PostResponse.builder()
                .title("Title")
                .content("Content")
                .build();

        // stub
        given(postRepository.findById(any())).willReturn(Optional.ofNullable(response));

        //when
        final PostResponse postResponse = postService.get(response.getId());

        //then
        assertThat(postResponse).isNotNull();
        assertThat(postResponse.getContent()).isEqualTo(oneResponse.getContent());
        assertThat(postResponse.getTitle()).isEqualTo(oneResponse.getTitle());
    }

    @Test
    @DisplayName("글 여러개 조회")
    void test_list() {
        //given
        final List<Post> posts = List.of(
                Post.builder()
                        .id(1L)
                        .title("Title1")
                        .content("Content1")
                        .build(),
                Post.builder()
                        .id(2L)
                        .title("Title2")
                        .content("Content2")
                        .build(),
                Post.builder()
                        .id(1L)
                        .title("Title3")
                        .content("Content3")
                        .build()
        );
        // stub
        given(postRepository.findAll()).willReturn(posts);

        //when
        final List<PostResponse> response = postService.getList();

        //then
        assertThat(response.size()).isEqualTo(3);
    }
}