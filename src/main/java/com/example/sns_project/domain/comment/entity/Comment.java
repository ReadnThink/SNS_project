package com.example.sns_project.domain.comment.entity;

import com.example.sns_project.domain.post.entity.Post;
import com.example.sns_project.domain.post.exception.InvalidRequest;
import com.example.sns_project.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String author;

    private String content;

    @ManyToOne
    @JoinColumn
    private Post post;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime lastModifiedAt;

    @Builder
    public Comment(final String content, final String author, final Post post) {
        this.content = content;
        this.author = author;
        this.post = post;
        this.createdAt = LocalDateTime.now();
        this.lastModifiedAt = LocalDateTime.now();
    }

    public void isValid() {
        if (this.content.contains("바보")) {
            throw new InvalidRequest();
        }

    }
}

