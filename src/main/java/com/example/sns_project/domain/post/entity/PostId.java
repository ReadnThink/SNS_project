package com.example.sns_project.domain.post.entity;

import com.example.sns_project.domain.Identifier;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@EqualsAndHashCode
@Getter
@Embeddable
public class PostId extends Identifier {
    private final String id;

    public PostId() {
        this.id = super.id;
    }

    /**
     * for Controller Constructor
     */
    public PostId(final String id) {
        this.id = id;
    }
}
