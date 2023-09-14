package com.example.sns_project.domain.user.entity;

import com.example.sns_project.domain.Identifier;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
@Embeddable
@AttributeOverride(name = "id", column = @Column(name = "userId"))
public class UserId extends Identifier {
    private final String id;

    public UserId() {
        id = super.getId();
    }

    /**
     * for Controller Constructor
     */
    public UserId(String uuid) {
        super(uuid);
        id = super.getId();
    }
}
