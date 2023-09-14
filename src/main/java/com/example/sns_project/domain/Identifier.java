package com.example.sns_project.domain;

import lombok.Getter;

import java.io.Serializable;
import java.util.UUID;

@Getter
public abstract class Identifier implements Serializable {
    private final String id;

    protected Identifier() {
        this.id = String.valueOf(UUID.randomUUID());
    }

    /**
     * for Controller Constructor
     */
    protected Identifier(String uuid) {
        this.id = uuid;
    }

}
