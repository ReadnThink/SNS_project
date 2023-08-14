package com.example.sns_project.service;

import com.example.sns_project.domain.post.application.PostService;
import com.example.sns_project.domain.post.dao.PostRepository;
import com.example.sns_project.domain.post.dto.PostCreate;
import com.example.sns_project.domain.post.dto.PostResponse;
import com.example.sns_project.domain.post.dto.PostSearch;
import com.example.sns_project.domain.post.entity.Post;
import com.example.sns_project.domain.post.exception.InvalidRequest;
import com.example.sns_project.domain.user.dao.UserRepository;
import com.example.sns_project.domain.user.entity.User;
import com.example.sns_project.domain.user.exception.UserNotFound;
import com.example.sns_project.domain.user.exception.UserNotMatch;
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
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;

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

        assertThat(responseDto.title()).isEqualTo("제목");
        assertThat(responseDto.content()).isEqualTo("내용");
    }

    @Test
    @DisplayName("글 작성 실패")
    void tes_실패1() {

        given(userRepository.findById(any())).willThrow(new UserNotFound());

        //when
        UserNotFound exception = assertThrows(UserNotFound.class, () -> postService.write(postCreate,1L));

        //then
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("존재하지 않는 이메일 입니다.", exception.getMessage());
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
        assertThat(postResponse.content()).isEqualTo(oneResponse.content());
        assertThat(postResponse.title()).isEqualTo(oneResponse.title());
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
        assertThat(list).hasSize(5);
    }

    @Test
    @DisplayName("글 수정 성공")
    void test_edit() {

        //when
        post.change("제목수정","내용수정");

        //then
        assertThat(post.getTitle()).isEqualTo("제목수정");
        assertThat(post.getContent()).isEqualTo("내용수정");
    }

    @Test
    @DisplayName("글 수정 실패 - 권한없음")
    void test_edit1() {
        //when
        var exception = assertThrows(UserNotMatch.class, () -> post.isSameUser(2L));

        //then
        assertEquals(FORBIDDEN, exception.getStatus());
        assertEquals("게시글을 수정/삭제는 게시글 작성자만 가능합니다.", exception.getMessage());
    }

    @Test
    @DisplayName("글 수정 실패 - 바보 포함")
    void test_edit2() {
        final Post valid = Post.builder()
                .title("천재")
                .content("바보")
                .build();

        //when
        var exception = assertThrows(InvalidRequest.class, valid::isValid);

        //then
        assertEquals(BAD_REQUEST, exception.getStatus());
        assertEquals("해당 단어는 포함될 수 없습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("글 삭제 성공")
    void test_delete() {
        /**
         * 서비스의 다른 로직은 JpaRepository 로직이므로 테스트 할 필요가 없다고 판단
         * 예외가 터지지 않으면 테스트는 성공
         */

        //when
        post.isSameUser(1L);
    }

    @Test
    @DisplayName("글 삭제 실패")
    void test_delete1() {

        /**
         * 서비스의 다른 로직은 JpaRepository 로직이므로 테스트 할 필요가 없다고 판단
         * 예외가 터지지 않으면 테스트는 성공
         */

        //when
        var exception = assertThrows(UserNotMatch.class, () -> post.isSameUser(2L));

        //then
        assertEquals(exception.getStatus(), HttpStatus.FORBIDDEN);
        assertEquals(exception.getMessage(), "게시글을 수정/삭제는 게시글 작성자만 가능합니다.");
    }
}