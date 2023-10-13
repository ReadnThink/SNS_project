package com.example.sns.domain.comment.exception;

import com.example.sns.config.exception.CustomApiException;
import org.springframework.http.HttpStatus;

public class CommentNotFound extends CustomApiException {

    private static final String MESSAGE = "존재하지 않는 댓글입니다.";

    public CommentNotFound() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
