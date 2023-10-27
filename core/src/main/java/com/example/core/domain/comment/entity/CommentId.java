package com.example.core.domain.comment.entity;

import com.example.core.domain.Identifier;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
@Embeddable
@AttributeOverride(name = "id", column = @Column(name = "commentId"))
public class CommentId extends Identifier {
    public CommentId() {}

    /**
     * for Controller Constructor
     */
    public CommentId(final String uuid) {
        super(uuid);
    }
}
