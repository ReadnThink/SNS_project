package com.example.sns_project.domain.post.entity;

import com.example.sns_project.domain.Identifier;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
@Embeddable
@AttributeOverride(name = "id", column = @Column(name = "postId"))
public class PostId extends Identifier {
    private final String id;

    public PostId() {
        id = super.getId();
    }

    /**
     * for Controller Constructor
     */
    public PostId(final String uuid) {
        super(uuid);
        id = super.getId();
    }
}