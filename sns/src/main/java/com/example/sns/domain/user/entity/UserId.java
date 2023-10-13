package com.example.sns.domain.user.entity;

import com.example.sns.domain.Identifier;
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
    public UserId() {}

    /**
     * for Controller Constructor
     */
    public UserId(String uuid) {
        super(uuid);
    }
}
