package com.example.sns_project.controller;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class PostCreate {
    @NotEmpty(message = "타이틀을 입력해주세요")
    @Size(max = 30, message = "내용은 30글자 이내로 입력 가능합니다.")
    private String title;

    @NotEmpty(message = "내용을 입력해주세요")
    private String content;

    public PostCreate(final String title, final String content) {
        this.title = title;
        this.content = content;
    }
}
