package com.example.sns_project.service;

import com.example.sns_project.domain.post.application.PostService;
import com.example.sns_project.domain.post.dao.PostRepository;
import com.example.sns_project.domain.post.dto.PostCreate;
import com.example.sns_project.domain.post.dto.PostResponse;
import com.example.sns_project.domain.post.dto.PostSearch;
import com.example.sns_project.domain.post.entity.Post;
import com.example.sns_project.domain.user.dao.UserRepository;
import com.example.sns_project.domain.user.entity.User;
import com.example.sns_project.domain.user.exception.UserNotFound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    PostRepository postRepository;
    @Mock
    UserRepository userRepository;
    User user;
    Post post;
    PostCreate postCreate;
    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .email("wanted@wanted.com")
                .password("12341234")
                .build();

        post = Post.builder()
                .user(user)
                .id(1L)
                .title("제목")
                .content("내용")
                .build();

        postCreate = PostCreate.builder()
                .title("제목")
                .content("내용")
                .build();

    }
    @Test
    @DisplayName("글 작성 성공")
    void test1() {

        given(postRepository.save(any())).willReturn(post);
        given(userRepository.findById(any())).willReturn(Optional.ofNullable(user));

        var responseDto = postService.write(PostCreate.builder()
                .title("제목")
                .content("내용")
                .build(), 1L);

        assertThat(responseDto.getTitle()).isEqualTo("제목");
        assertThat(responseDto.getContent()).isEqualTo("내용");
    }

    @Test
    @DisplayName("글 작성 실패")
    void tes_실패1() {

        given(userRepository.findById(any())).willThrow(new UserNotFound());

        //when
        UserNotFound exception = assertThrows(UserNotFound.class, () -> postService.write(postCreate,1L));

        //then
        assertEquals(exception.getStatus(), HttpStatus.NOT_FOUND);
        assertEquals(exception.getMessage(), "존재하지 않는 이메일 입니다.");
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
    @DisplayName("글 1페이지 조회")
    void test_list() {
        //given
        List<Post> response = IntStream.range(1, 6)
                .mapToObj(i -> {
                    return Post.builder()
                            .title("Title " + i)
                            .content("Content " + i)
                            .build();
                })
                .collect(Collectors.toList());
        // stub
        given(postRepository.getList(any())).willReturn(response);

        //when
        PostSearch postSearch = PostSearch.builder().build();
        final List<PostResponse> list = postService.getList(postSearch);

        //then
        assertThat(list.size()).isEqualTo(5);
    }

    @Test
    @DisplayName("글 수정 성공")
    void test_edit() {
        //given
        final Post post = Post.builder()
                .id(1L)
                .title("Title")
                .content("Content")
                .build();

        //when
        post.change("제목수정","내용수정");

        //then
        assertThat(post.getTitle()).isEqualTo("제목수정");
        assertThat(post.getContent()).isEqualTo("내용수정");
    }

}