package com.example.core.infra.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusCode {
    SUCCESS("200"),
    BAD_REQUEST("400"),
    FORBIDDEN("403"),
    NOT_FOUND("404"),
    ;

    private final String value;
}
