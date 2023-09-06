package com.example.sns_project.domain.post.dto;

import com.example.sns_project.domain.post.entity.Post;
import com.example.sns_project.domain.post.entity.PostId;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;

public record PostCreate(
        @NotEmpty(message = "타이틀을 입력해주세요")
        @Size(max = 30, message = "내용은 30글자 이내로 입력 가능합니다.")
        String title,
        @NotEmpty(message = "내용을 입력해주세요")
        String content

) {
    @Builder
    public PostCreate(final String title, final String content) {
        this.title = title;
        this.content = content;
    }

    public Post toEntity() {
        return Post.builder()
                .id(new PostId())
                .title(this.title)
                .content(this.content)
                .build();
    }
}
