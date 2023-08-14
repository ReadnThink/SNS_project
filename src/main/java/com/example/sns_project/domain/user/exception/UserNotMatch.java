package com.example.sns_project.domain.user.exception;

import com.example.sns_project.global.exception.CustomApiException;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.FORBIDDEN;

public class UserNotMatch extends CustomApiException {
    private static final String MESSAGE = "게시글을 수정/삭제는 게시글 작성자만 가능합니다.";

    public UserNotMatch() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getStatus() {
        return FORBIDDEN;
    }
}
