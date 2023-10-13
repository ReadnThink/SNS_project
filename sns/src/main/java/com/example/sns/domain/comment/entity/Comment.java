package com.example.sns.domain.comment.entity;

import com.example.sns.config.exception.InvalidRequest;
import com.example.sns.domain.post.entity.PostId;
import com.example.sns.infra.util.BanWords;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
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
    @EmbeddedId
    @Column(name = "commentId")
    private CommentId commentId;
    private String author;
    private String content;
    private PostId postId;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime lastModifiedAt;

    @Builder
    public Comment(CommentId commentId, final String content, final String author, final PostId postId) {
        if (commentId == null) {
            commentId = new CommentId();
        }
        this.commentId = commentId;
        this.content = content;
        this.author = author;
        this.postId = postId;
        this.createdAt = LocalDateTime.now();
        this.lastModifiedAt = LocalDateTime.now();
        isValid();
    }

    public void isValid() {
        if (Arrays.stream(BanWords.values())
                .anyMatch(v -> v.getValue().contains(this.content))) {
            throw new InvalidRequest();
        }
    }

    public void isValid(String content) {
        if (Arrays.stream(BanWords.values())
                .anyMatch(v -> v.getValue().contains(this.content))) {
            throw new InvalidRequest();
        }
    }

    public boolean isSameUser(final String userEmail) {
        isValid();
        return this.author.equals(userEmail);
    }

    public void editComment(final String comment) {
        isValid(comment);
        this.content = comment;
        this.lastModifiedAt = LocalDateTime.now();
    }
}

