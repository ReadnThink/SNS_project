package com.example.sns_project.domain.post.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.Objects;

@Getter
@Embeddable
public class PostId {
    public static Long POST_SEQUENCE = 0L;
    private Long postId;

    public PostId(final String postId) {
        this.postId = Long.valueOf(postId);
    }

    public PostId() {
        this.postId = POST_SEQUENCE++;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final PostId postId1 = (PostId) o;

        return Objects.equals(postId, postId1.postId);
    }

    @Override
    public int hashCode() {
        return postId != null ? postId.hashCode() : 0;
    }
}
