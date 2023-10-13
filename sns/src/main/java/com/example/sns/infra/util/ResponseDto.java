package com.example.sns.infra.util;

import lombok.Builder;

@Builder
public record ResponseDto<T>(String message, T data) {

    public static <T> ResponseDto<T> success() {
        return ResponseDto.<T>builder()
                .message("SUCCESS")
                .build();
    }

    public static <T> ResponseDto<T> success(T data) {
        return ResponseDto.<T>builder()
                .message("SUCCESS")
                .data(data)
                .build();
    }

    public static <T> ResponseDto<T> success(String message, T data) {
        return ResponseDto.<T>builder()
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ResponseDto<T> error(String message, T data) {
        return ResponseDto.<T>builder()
                .message(message)
                .data(data)
                .build();
    }
}

