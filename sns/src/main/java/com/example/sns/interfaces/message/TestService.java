package com.example.sns.interfaces.message;

import com.example.core.dto.KafkaMsgVO;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    private final TestCreateProducer testCreateProducer;
    private int count = 0;

    public TestService(final TestCreateProducer testCreateProducer) {
        this.testCreateProducer = testCreateProducer;
    }

    public void apply(KafkaMsgVO userId) {
        count++;

        if (count > 100) {
            return;
        }

        testCreateProducer.create(userId);
    }
}
