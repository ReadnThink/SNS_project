package com.example.sns.interfaces.message;

import com.example.core.dto.KafkaMsgVO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TestCreateProducer {

    private final KafkaTemplate<String, KafkaMsgVO> kafkaTemplate;


    public TestCreateProducer(final KafkaTemplate<String, KafkaMsgVO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void create(KafkaMsgVO userId) {
        kafkaTemplate.send("coupon_create", userId);
    }
}
