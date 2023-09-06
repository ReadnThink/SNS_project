package com.example.sns_project.domain.user.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Embeddable
public class UserId implements Serializable {

    private String UserId;
    public UserId() {
        UserId = String.valueOf(UUID.randomUUID());
    }

    public UserId(String uuid) {
        UserId = uuid;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final com.example.sns_project.domain.user.entity.UserId userid = (com.example.sns_project.domain.user.entity.UserId) o;

        return Objects.equals(UserId, userid.UserId);
    }

    @Override
    public int hashCode() {
        return UserId != null ? UserId.hashCode() : 0;
    }
}
