package com.example.sns_project.domain.user.entity;

import com.example.sns_project.domain.Identifier;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
@Embeddable
public class UserId extends Identifier {
    private String userId;

    public UserId() {
        userId = super.makeIdentifier();
    }

    /**
     * for Controller Constructor
     */
    public UserId(String uuid) {
        userId = uuid;
    }
}
