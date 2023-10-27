package com.example.core.domain.messaging;

public class MassagingVO {
    public static final String COMMAND_GATEWAY_CHANNEL = "CommandChannel";
    public static final String EVENT_GATEWAY_CHANNEL = "EventChannel";
    public static final String COMMAND_GATEWAY_POST_CREATE_CHANNEL = "PostCreateMessage";
    public static final String EVENT_GATEWAY_POST_CREATE_CHANNEL = "KafkaPostCreate";
    public static final String EVENT_GATEWAY_POST_EDIT_CHANNEL = "KafkaPostEdit";
    public static final String EVENT_GATEWAY_POST_DELETE_CHANNEL = "KafkaPostDelete";
    public static final String COMMAND_GATEWAY_POST_EDIT_CHANNEL = "PostEditMessage";
    public static final String COMMAND_GATEWAY_POST_DELETE_CHANNEL = "PostDeleteMessage";
    public static final String EVENT_GATEWAY_POST_GET_CHANNEL = "PostResponse";
    public static final String COMMAND_GATEWAY_COMMENT_CREATE_CHANNEL = "CommentCreateMessage";
    public static final String COMMAND_GATEWAY_COMMENT_EDIT_CHANNEL = "CommentEditMessage";
    public static final String COMMAND_GATEWAY_COMMENT_DELETE_CHANNEL = "CommentDeleteMessage";
    public static final String EVENT_GATEWAY_COMMENT_GET_CHANNEL = "CommentResponse";
    public static final String EVENT_GATEWAY_COMMENT_CREATE_CHANNEL = "KafkaCommentCreate";
    public static final String EVENT_GATEWAY_COMMENT_EDIT_CHANNEL = "KafkaCommentEdit";
    public static final String EVENT_GATEWAY_COMMENT_Delete_CHANNEL = "KafkaCommentDelete";
}
