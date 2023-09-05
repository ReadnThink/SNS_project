package com.example.sns_project.domain.user.entity;

import com.example.sns_project.domain.post.entity.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User{

    @EmbeddedId
    private UserId id;

    private String name;

    private String email;

    private String password;

    private UserRole userRole;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Post> posts;

    private LocalDateTime createdAt;

    private LocalDateTime lastModifiedAt;

    public UserId getId() {
        return id;
    }

    @Builder
    public User(UserId id, final String name, final String email, final String password, final  UserRole userRole) {
        if (id == null) {
            id = new UserId();
        }
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
        this.createdAt = LocalDateTime.now();
        this.lastModifiedAt = LocalDateTime.now();
    }
}
