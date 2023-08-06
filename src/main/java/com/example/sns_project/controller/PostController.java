package com.example.sns_project.controller;

import com.example.sns_project.config.data.UserSession;
import com.example.sns_project.request.PostCreate;
import com.example.sns_project.request.PostEdit;
import com.example.sns_project.response.PostResponse;
import com.example.sns_project.request.PostSearch;
import com.example.sns_project.response.ResponseDto;
import com.example.sns_project.service.PostService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
public class PostController {

    private final PostService postService;

    public PostController(final PostService postService) {
        this.postService = postService;
    }

    // 임시 라우터
    @GetMapping("/foo")
    public long test(UserSession userSession) {
        log.info("디버그 : {}", userSession.getId());
        return userSession.getId();
    }

    @GetMapping("/bar")
    public String test1() {
        return "인증이 필요 없는 페이지";
    }

    @PostMapping("/posts")
    public ResponseEntity<?> post(@RequestBody @Valid PostCreate postCreate, BindingResult bindingResult) {
        postCreate.isValid();
        postService.write(postCreate);
        return new ResponseEntity<>(ResponseDto.builder()
                .code(1)
                .message("글 작성을 성공했습니다.")
                .build(), OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<?> get(@PathVariable Long postId) {
        final PostResponse postResponse = postService.get(postId);
        return new ResponseEntity<>(ResponseDto.builder()
                .code(1)
                .message("글 조회에 성공했습니다.")
                .data(postResponse)
                .build(), OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<?> getList(@ModelAttribute PostSearch postSearch) {
        final List<PostResponse> postList = postService.getList(postSearch);
        return new ResponseEntity<>(ResponseDto.builder()
                .code(1)
                .message("글 리스트 조회를 성공했습니다.")
                .data(postList)
                .build(), OK);
    }

    @PatchMapping("/posts/{postId}")
    public ResponseEntity<?> edit(@PathVariable Long postId, @RequestBody PostEdit postEdit) {
        postService.edit(postId, postEdit);
        return new ResponseEntity<>(ResponseDto.builder()
                .code(1)
                .message("글 수정을 성공했습니다.")
                .build(), OK);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<?> delete(@PathVariable Long postId) {
        postService.delete(postId);
        return new ResponseEntity<>(ResponseDto.builder()
                .code(1)
                .message("글 삭제를 성공했습니다.")
                .build(), OK);
    }
}
