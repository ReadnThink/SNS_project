package com.example.sns_project.controller;

import com.example.sns_project.application.PostService;
import com.example.sns_project.domain.post.dto.PostCreate;
import com.example.sns_project.domain.post.dto.PostEdit;
import com.example.sns_project.domain.post.dto.PostResponse;
import com.example.sns_project.domain.post.entity.PostId;
import com.example.sns_project.domain.post.exception.PostNotFound;
import com.example.sns_project.domain.user.UserRepository;
import com.example.sns_project.domain.user.entity.User;
import com.example.sns_project.domain.user.entity.UserRole;
import com.example.sns_project.config.exception.CustomApiException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.example.sns_project.config.PostUrl.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper om;

    @MockBean
    PostService postService;

    @Autowired
    UserRepository userRepository;
    PostId postId;

    @BeforeEach
    public void setUp(){
        saveMockUser();
        postId = new PostId("1");
    }

    @Test
    @DisplayName("글 작성 성공")
    @WithUserDetails(value = "sns@sns.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void 작성성공1() throws Exception {
        // when
        var postCreate = PostCreate.builder()
                .title("제목")
                .content("내용")
                .build();

        var postResponse = PostResponse.builder()
                .id(postId)
                .title("제목")
                .content("내용")
                .build();

        given(postService.write(any(),any())).willReturn(postResponse);

        mockMvc.perform(post(POST_CREATE_URL.getValue())
                        .contentType(APPLICATION_JSON)
                        .content(om.writeValueAsString(postCreate))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("SUCCESS"))
                .andExpect(jsonPath("$.data.id.postId").value(postId.getPostId()))
                .andExpect(jsonPath("$.data.title").value("제목"))
                .andExpect(jsonPath("$.data.content").value("내용"))
                .andDo(print())
        ;
    }

    @Test
    @DisplayName("글 작성 title 빈 문자열 불가")
    @WithUserDetails(value = "sns@sns.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void 작성2() throws Exception {
        var postCreate = PostCreate.builder()
                .title("")
                .content("내용")
                .build();
        mockMvc.perform(post(POST_CREATE_URL.getValue())
                        .contentType(APPLICATION_JSON)
                        .content(om.writeValueAsBytes(postCreate))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("타이틀을 입력해주세요"))
                .andExpect(jsonPath("$.data").isEmpty())
                .andDo(print())
        ;
    }
    @Test
    @DisplayName("글 작성 글의 제목은 제한 없음")
    @WithUserDetails(value = "sns@sns.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void 작성3() throws Exception {
        var postCreate = PostCreate.builder()
                .title("제목 글자수 제한이 없습니다.제목 글자수 제한이 없습니다.제목 글자수 제한이 없습니다.제목 글자수 제한이 없습니다.제목 글자수 제한이 없습니다.제목 글자수 제한이 없습니다.")
                .content("내용")
                .build();

        mockMvc.perform(post(POST_CREATE_URL.getValue())
                        .contentType(APPLICATION_JSON)
                        .content(om.writeValueAsBytes(postCreate))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("내용은 30글자 이내로 입력 가능합니다."))
                .andExpect(jsonPath("$.data").isEmpty())
                .andDo(print())
        ;
    }

    @Test
    @DisplayName("글 작성 실패 - 로그인 하지 않음")
    void 작성4() throws Exception {
        var postCreate = PostCreate.builder()
                .title("WithUserDetails 없음")
                .content("내용")
                .build();

        mockMvc.perform(post(POST_CREATE_URL.getValue())
                        .contentType(APPLICATION_JSON)
                        .content(om.writeValueAsBytes(postCreate))
                )
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value("권한이 없습니다."))
                .andDo(print())
        ;
    }

    @Test
    @DisplayName("글 1개 조회 - 성공")
    void 조회성공1() throws Exception {
        String postId_PathValue = "1";
        //when
        mockMvc.perform(get(POST_GET_URL.getValue(), postId_PathValue)
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("SUCCESS"))
                .andExpect(jsonPath("$.data").isEmpty())
                .andDo(print())
        ;
        verify(postService).get(postId);
    }

    @Test
    @DisplayName("글 1개 조회 - 실패")
    void 조회실패() throws Exception {
        //given
        String postId_PathValue = "1";
        final CustomApiException customApiException = new PostNotFound();

        //stub
        given(postService.get(postId)).willThrow(customApiException);

        //when
        mockMvc.perform(get(POST_GET_URL.getValue(), postId_PathValue)
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("존재하지 않는 글입니다."))
                .andExpect(jsonPath("$.data").isEmpty())
                .andDo(print())
        ;
        verify(postService).get(postId);
    }

    @Test
    @DisplayName("글 여러개 조회 - 성공")
    void 글_여러개조회성공() throws Exception {
        //given
        List<PostResponse> requestPosts = IntStream.range(1, 11)
                .mapToObj(i -> {
                    return PostResponse.builder()
                            .title("Title " + i)
                            .content("Content " + i)
                            .build();
                })
                .collect(Collectors.toList());
        //stub
        given(postService.getList(any())).willReturn(requestPosts);

        //when
        mockMvc.perform(get(POST_LIST_URL.getValue())
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("SUCCESS"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.size()").value(10))
                .andDo(print())
        ;
        verify(postService).getList(any());
    }

    @Test
    @DisplayName("글 수정 성공")
    @WithUserDetails(value = "sns@sns.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void 글_수정() throws Exception {
        //given
        var request = PostEdit.builder()
                .title("수정")
                .content("수정")
                .build();
        //when
        mockMvc.perform(post(POST_EDIT_URL.getValue(), 1L)
                        .contentType(APPLICATION_JSON)
                        .content(om.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("SUCCESS"))
                .andExpect(jsonPath("$.data").isEmpty())
                .andDo(print())
        ;
        verify(postService).edit(any(), any(), any());
    }

    @Test
    @DisplayName("글 수정 실패 - 로그인하지 않음")
    void 글_수정1() throws Exception {
        //given
        var request = PostEdit.builder()
                .title("WithUserDetails 없음")
                .content("수정")
                .build();
        //when
        mockMvc.perform(patch(POST_EDIT_URL.getValue(), 1L)
                        .contentType(APPLICATION_JSON)
                        .content(om.writeValueAsString(request))
                )
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value("권한이 없습니다."))
                .andDo(print())
        ;
    }

    @Test
    @DisplayName("글 삭제 성공")
    @WithUserDetails(value = "sns@sns.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void 글_삭제() throws Exception {
        //when
        mockMvc.perform(delete(POST_DELETE_URL.getValue(), 1L)
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("SUCCESS"))
                .andExpect(jsonPath("$.data").isEmpty())
                .andDo(print())
        ;
        verify(postService).delete(any(), any());
    }

    @Test
    @DisplayName("글 삭제 실패 - 로그인하지 않음")
    void 글_삭제1() throws Exception {
        //when
        mockMvc.perform(delete(POST_DELETE_URL.getValue(), 1L)
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value("권한이 없습니다."))
                .andExpect(jsonPath("$.data").isEmpty())
                .andDo(print())
        ;
    }

    private void saveMockUser() {
        User user = User.builder()
                .email("sns@sns.com")
                .userRole(UserRole.USER)
                .password("12341234")
                .build();
        userRepository.save(user);
    }
}