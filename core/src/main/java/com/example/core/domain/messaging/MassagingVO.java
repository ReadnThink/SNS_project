package com.example.core.domain.messaging;

public class MassagingVO {
    public static final String COMMAND_GATEWAY_CHANNEL = "CommandChannel";
    public static final String EVENT_GATEWAY_CHANNEL = "EventChannel";
    public static final String COMMAND_GATEWAY_POST_CREATE_CHANNEL = "KafkaPostCreate";
    public static final String COMMAND_GATEWAY_POST_EDIT_CHANNEL = "KafkaPostEdit";
    public static final String COMMAND_GATEWAY_POST_DELETE_CHANNEL = "KafkaPostDelete";
    public static final String EVENT_GATEWAY_POST_GET_CHANNEL = "PostResponse";
    public static final String COMMAND_GATEWAY_COMMENT_CREATE_CHANNEL = "CommentCreate";
    public static final String EVENT_GATEWAY_COMMENT_GET_CHANNEL = "CommentResponse";
    public static final String MESSAGE_USER_ID = "userId";
    public static final String MESSAGE_POST_ID = "postId";
}
