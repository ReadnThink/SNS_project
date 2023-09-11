package com.example.sns_project.domain.user.entity;

import com.example.sns_project.domain.Identifier;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;
import java.util.UUID;

@EqualsAndHashCode
@Getter
@Embeddable
public class UserId extends Identifier {
    private String id;

    public UserId() {
        id = super.id;
    }

    /**
     * for Controller Constructor
     */
    public UserId(String uuid) {
        id = uuid;
    }
}
