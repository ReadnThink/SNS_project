package com.example.sns_project.config.aop;

import com.example.sns_project.domain.messaging.event.Events;
import com.example.sns_project.domain.comment.entity.Comment;
import com.example.sns_project.domain.post.entity.Post;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Optional;

@Slf4j
@Aspect
@Component
public class CommandHandlerAspect {

    @Before("@annotation(com.example.sns_project.config.aop.CommandAop)")
    public void before(JoinPoint joinPoint) {
        log.info("---------------AOP CommandHandlerAspect with TransactionSynchronizationManager start!---------------");
        TransactionSynchronizationManager.registerSynchronization(new EventsTransactionSynchronization());
    }

    public class EventsTransactionSynchronization implements TransactionSynchronization {

        @Override
        public void afterCommit() {
            log.info("---------------Transaction Success!!---------------");
            // todo Event 채널 만들어서 Event 채널로 보내기
            Events.getEvents().stream()
                    .map(e -> (Post) e)
                    .forEach(post -> {
                        System.out.println(post.getContent());
                    });
            Events.clear();
        }
    }
}