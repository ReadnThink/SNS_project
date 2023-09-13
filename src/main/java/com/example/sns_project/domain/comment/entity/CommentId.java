package com.example.sns_project.domain.comment.entity;

import com.example.sns_project.domain.Identifier;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
@Embeddable
public class CommentId extends Identifier {
    private final String commentId;

    public CommentId() {
        commentId = super.makeIdentifier();
    }

    /**
     * for Controller Constructor
     */
    public CommentId(final String uuid) {
        this.commentId = uuid;
    }
}
