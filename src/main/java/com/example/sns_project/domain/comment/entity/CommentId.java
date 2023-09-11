package com.example.sns_project.domain.comment.entity;

import com.example.sns_project.domain.Identifier;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;
import java.util.UUID;

@EqualsAndHashCode
@Getter
@Embeddable
public class CommentId extends Identifier {
    private final String id;

    public CommentId() {
        id = super.id;
    }

    /**
     * for Controller Constructor
     */
    public CommentId(final String id) {
        this.id = id;
    }
}
