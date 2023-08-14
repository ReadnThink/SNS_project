package com.example.sns_project.domain.post.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ResponseDto<T> {
    private final Integer code;
    private final String message;
    private final T data;
    private final Map<String, String> validation;

    @Builder
    public ResponseDto(final Integer code, final String message, final T data, Map<String, String> validation) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.validation = validation;
    }
}

