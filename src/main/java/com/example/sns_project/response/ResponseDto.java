package com.example.sns_project.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class ResponseDto<T> {
    private final Integer code;
    private final String message;
    private final T data;

    @Builder
    public ResponseDto(final Integer code, final String message, final T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}

