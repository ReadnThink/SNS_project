package com.example.sns.application;

import com.example.core.domain.KafkaPostCreate;
import com.example.sns.interfaces.message.PostCreateProducer;
import org.springframework.stereotype.Service;

@Service
public class KafkaPostService {
    private final PostCreateProducer postCreateProducer;
    private int count = 0;

    public KafkaPostService(final PostCreateProducer postCreateProducer) {
        this.postCreateProducer = postCreateProducer;
    }

    public void apply(KafkaPostCreate userId) {
        count++;

        if (count > 10) {
            return;
        }

        postCreateProducer.create(userId);
    }
}
