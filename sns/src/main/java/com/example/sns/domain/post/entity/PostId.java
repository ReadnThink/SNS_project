package com.example.sns.domain.post.entity;

import com.example.sns.domain.Identifier;
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
    public PostId() {}

    /**
     * for Controller Constructor
     */
    public PostId(final String uuid) {
        super(uuid);
    }
}