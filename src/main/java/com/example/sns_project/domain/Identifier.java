package com.example.sns_project.domain;

import java.io.Serializable;
import java.util.UUID;


public abstract class Identifier implements Serializable {
    public String makeIdentifier() {
        return String.valueOf(UUID.randomUUID());
    }
}
