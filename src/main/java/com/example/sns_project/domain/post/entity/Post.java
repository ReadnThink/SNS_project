package com.example.sns_project.domain.post.entity;

import com.example.sns_project.config.util.BanWords;
import com.example.sns_project.domain.comment.entity.Comment;
import com.example.sns_project.config.exception.InvalidRequest;
import com.example.sns_project.domain.user.entity.User;
import com.example.sns_project.domain.user.entity.UserId;
import com.example.sns_project.domain.user.exception.UserNotMatch;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private String title;
    @ManyToOne
    @JoinColumn
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private List<Comment> comments;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime lastModifiedAt;

    @Builder
    public Post(final Long id, final String content, final String title, User user) {
        this.id = id;
        this.content = content;
        this.title = title;
        this.user = user;
        this.createdAt = LocalDateTime.now();
        this.lastModifiedAt = LocalDateTime.now();
    }

    public void editPost(String title, String content) {
        isValid(title, content);
        this.title = title;
        this.content = content;
        this.lastModifiedAt = LocalDateTime.now();
    }

    public void addUser(final User user) {
        this.user = user;
    }

    public void isSameUser(final UserId userId) {
        if (this.user == null || !this.user.getId().equals(userId) ) {
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
