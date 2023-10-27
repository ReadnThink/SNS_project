package com.example.core.domain.messaging.command.comment.kafka;

import com.example.core.domain.comment.entity.Comment;
import com.example.core.domain.comment.entity.CommentId;
import com.example.core.domain.messaging.event.Event;
import com.example.core.domain.post.entity.PostId;

public record KafkaCommentCreate(CommentId commentId, PostId postId, String author, String content) implements Event {

    public Comment toEntity() {
        return Comment.builder()
                .commentId(commentId)
                .postId(postId)
                .author(author)
                .content(content)
                .build();
    }
}
