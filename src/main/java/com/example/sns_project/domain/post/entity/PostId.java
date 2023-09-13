package com.example.sns_project.domain.post.entity;

import com.example.sns_project.domain.Identifier;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
@Embeddable
public class PostId extends Identifier {
    private final String postId;

    public PostId() {
        this.postId = super.makeIdentifier();
    }

    /**
     * for Controller Constructor
     */
    public PostId(final String uuid) {
        this.postId = uuid;
    }
}