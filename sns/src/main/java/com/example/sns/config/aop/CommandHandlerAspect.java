package com.example.sns.config.aop;

import com.example.core.domain.messaging.event.Events;
import com.example.sns.interfaces.message.PostCreateProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@Aspect
@Component
public class CommandHandlerAspect {
    private final PostCreateProducer postCreateProducer;
    private final ObjectMapper om;

    public CommandHandlerAspect(final PostCreateProducer postCreateProducer, final ObjectMapper om) {
        this.postCreateProducer = postCreateProducer;
        this.om = om;
    }

    @Before("@annotation(com.example.core.config.aop.CommandAop)")
    public void before(JoinPoint joinPoint) {
        log.info("---------------AOP CommandHandlerAspect with TransactionSynchronizationManager start!---------------");
        TransactionSynchronizationManager.registerSynchronization(new EventsTransactionSynchronization());
    }

    public class EventsTransactionSynchronization implements TransactionSynchronization {

        @Override
        public void afterCommit() {
            log.info("---------------Transaction Success!! now in afterCommit---------------");
            Events.getEvents()
                    .forEach(event -> {
                        log.info("send message to topic : " + event.getClass().getSimpleName());
                        try {
                            postCreateProducer.postCreate(om.writeValueAsString(event));
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                    });
            Events.clear();
        }
    }
}