package com.example.sns_project.domain.comment.entity;

import com.example.sns_project.config.util.BanWords;
import com.example.sns_project.domain.post.entity.Post;
import com.example.sns_project.config.exception.InvalidRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Arrays;


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
        if (Arrays.stream(BanWords.values())
                .anyMatch(v -> v.getValue().contains(this.content))) {
            throw new InvalidRequest();
        }
    }

    public boolean isSameUser(final String name) {
        isValid();
        return this.getAuthor().equals(name);
    }

    public void editComment(final String comment) {
        this.content = comment;
    }
}

