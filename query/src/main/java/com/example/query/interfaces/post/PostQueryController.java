package com.example.query.interfaces.post;

import com.example.core.domain.post.dto.PostResponse;
import com.example.core.domain.post.entity.PostId;
import com.example.core.infra.util.ResponseDto;
import com.example.query.application.PostFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class PostQueryController {
    private final PostFacade postFacade;

    public PostQueryController(final PostFacade postFacade) {
        this.postFacade = postFacade;
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<ResponseDto<PostResponse>> get(@ModelAttribute PostId postId) {
        log.info("\"/posts\" get");
        return ResponseEntity.ok(ResponseDto.success(postFacade.get(postId)));
    }

    @GetMapping("/posts")
    public ResponseEntity<ResponseDto<List<PostResponse>>> getList(Pageable pageable) {
        log.info("\"/posts\" getList");

        return ResponseEntity.ok(ResponseDto.success(postFacade.getList(pageable)));
    }
}
