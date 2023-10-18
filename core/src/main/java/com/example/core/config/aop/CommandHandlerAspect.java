package com.example.core.config.aop;

import com.example.core.config.messaging.gateway.EventGateway;
import com.example.core.domain.messaging.event.Events;
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
    private final EventGateway eventGateway;

    public CommandHandlerAspect(final EventGateway eventGateway) {
        this.eventGateway = eventGateway;
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
            // todo Event 채널 만들어서 Event 채널로 보내기
            Events.getEvents().stream()
                    .forEach(event -> {
                        log.info("router : " + event.getClass().getSimpleName());
                        eventGateway.request(event);
                    });
            Events.clear();
        }
    }
}