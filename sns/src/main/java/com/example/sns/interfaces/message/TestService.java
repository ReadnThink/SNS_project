package com.example.sns.interfaces.message;

import org.springframework.stereotype.Service;

@Service
public class TestService {

    private final TestCreateProducer testCreateProducer;
    private int count = 0;

    public TestService(final TestCreateProducer testCreateProducer) {
        this.testCreateProducer = testCreateProducer;
    }

    public void apply(Long userId) {
        count++;

        if (count > 100) {
            return;
        }

        testCreateProducer.create(userId);
    }
}
