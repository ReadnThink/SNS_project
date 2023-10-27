package com.example.query.interfaces.comment;

import com.example.core.domain.comment.dto.CommentResponse;
import com.example.core.domain.comment.entity.CommentId;
import com.example.core.infra.util.ResponseDto;
import com.example.query.application.facade.CommentFacade;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class CommentQueryController {
    private final CommentFacade commentFacade;

    public CommentQueryController(final CommentFacade commentFacade) {
        this.commentFacade = commentFacade;
    }

    // todo StatusCode 상황에 맞게 설계하기
    @GetMapping("/comments/{commentId}")
    public ResponseEntity<ResponseDto<CommentResponse>> getComment(@ModelAttribute final CommentId commentId) {
        return ResponseEntity.ok(ResponseDto.success(commentFacade.get(commentId)));
    }

    // todo 전체 댓글 리스트가 아닌 특정 댓글 리스트 API 필요
    @GetMapping("/comments")
    public ResponseEntity<ResponseDto<List<CommentResponse>>> commentList(Pageable pageable) {
        return ResponseEntity.ok(ResponseDto.success(commentFacade.getList(pageable)));
    }
}
