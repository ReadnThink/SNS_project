package com.example.sns_project.global.util;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseDto2<T> {
    private final String code; // 1 성공, - 1 실패
    private final String message;
    private final T data;

    @Builder
    public ResponseDto2(final String code, final String message, final T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}

