package com.example.core.domain;

import com.example.core.domain.user.entity.UserId;
import com.example.core.interfaces.post.dto.PostCreate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class KafkaPostCreate {
    private PostCreate postCreate;
    private UserId userId;
}
