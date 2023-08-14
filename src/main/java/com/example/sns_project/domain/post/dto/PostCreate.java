package com.example.sns_project.domain.post.dto;

import com.example.sns_project.domain.post.entity.Post;
import com.example.sns_project.global.exception.InvalidRequest;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@ToString
public class PostCreate {
    @NotEmpty(message = "타이틀을 입력해주세요")
    @Size(max = 30, message = "내용은 30글자 이내로 입력 가능합니다.")
    private String title;

    @NotEmpty(message = "내용을 입력해주세요")
    private String content;

    @Builder
    public PostCreate(final String title, final String content) {
        this.title = title;
        this.content = content;
    }

    public Post toEntity() {
        return Post.builder()
                .title(this.title)
                .content(this.content)
                .build();
    }

    public void isValid() {
        if (this.title.contains("바보")) {
            throw new InvalidRequest("title", "제목에 바보를 포함할 수 없습니다.");
        }

    }
}
