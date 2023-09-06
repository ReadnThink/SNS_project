package com.example.sns_project.domain.comment.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Embeddable
public class CommentId implements Serializable {

    // todo commentId를 생성할 다른방법이 있지 않을까?
    public static Long COMMENT_SEQUENCE = 0L;
    private Long CommentId;

    /**
     * for Controller Constructor
     */
    public CommentId(final String commentId) {
        CommentId = Long.valueOf(commentId);
    }

    public CommentId() {
        CommentId = COMMENT_SEQUENCE++;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final CommentId commentId = (CommentId) o;

        return Objects.equals(CommentId, commentId.CommentId);
    }

    @Override
    public int hashCode() {
        return CommentId != null ? CommentId.hashCode() : 0;
    }
}
