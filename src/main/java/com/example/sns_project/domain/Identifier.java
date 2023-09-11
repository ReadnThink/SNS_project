package com.example.sns_project.domain;

import java.io.Serializable;
import java.util.UUID;


public abstract class Identifier implements Serializable {

    public final String id;

    public Identifier() {
        id = String.valueOf(UUID.randomUUID());
    }
}
