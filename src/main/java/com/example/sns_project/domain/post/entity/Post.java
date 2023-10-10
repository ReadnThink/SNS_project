package com.example.sns_project.domain.post.entity;

import com.example.sns_project.config.exception.InvalidRequest;
import com.example.sns_project.domain.messaging.event.Event;
import com.example.sns_project.infra.util.BanWords;
import com.example.sns_project.domain.comment.entity.CommentId;
import com.example.sns_project.domain.user.entity.UserId;
import com.example.sns_project.domain.user.exception.UserNotMatch;
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

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {
    @EmbeddedId
    @Column(name = "postId")
    private PostId postId;
    private String content;
    private String title;
    private UserId userId;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime lastModifiedAt;

    @Builder
    public Post(PostId postId, final String content, final String title, UserId userId) {
        if (postId == null) {
            postId = new PostId();
        }
        this.postId = postId;
        this.content = content;
        this.title = title;
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
        this.lastModifiedAt = LocalDateTime.now();
    }

    public void editPost(String title, String content) {
        isValid(title, content);
        this.title = title;
        this.content = content;
        this.lastModifiedAt = LocalDateTime.now();
    }

    public void addUser(final UserId userId) {
        this.userId = userId;
    }

    public void isSameUser(final UserId userId) {
        if (this.userId == null || !this.userId.equals(userId) ) {
            throw new UserNotMatch();
        }
    }

    public void isValid() {
        // todo 한번의 람다로 모두 처리하고싶다...
        searchBanWords(this.title);
        searchBanWords(this.content);
    }
    public void isValid(String title, String content) {
        searchBanWords(title);
        searchBanWords(content);
    }

    private void searchBanWords(String value) {
        if (Arrays.stream(BanWords.values())
                .anyMatch(v -> v.getValue().contains(value))) {
            throw new InvalidRequest();
        }
    }
}
