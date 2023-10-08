package com.example.sns_project.infra.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BanWords {
    STUPID("바보"),
    IDIOT("멍청이")
    ;

    private final String value;
}
