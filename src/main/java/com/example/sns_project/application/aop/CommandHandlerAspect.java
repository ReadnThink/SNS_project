package com.example.sns_project.application.aop;

import com.example.sns_project.config.messaging.event.Events;
import com.example.sns_project.domain.comment.entity.Comment;
import com.example.sns_project.domain.post.entity.Post;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Aspect
@Component
public class CommandHandlerAspect {

    @Before("@annotation(CommandAop)")
    public void before(JoinPoint joinPoint) {
        final Optional<TransactionSynchronization> eventTransaction = TransactionSynchronizationManager.getSynchronizations().stream()
                .filter(transactionSynchronization -> transactionSynchronization instanceof EventsTransactionSynchronization)
                .findAny();
        log.info("---------------AOP CommandHandlerAspect with TransactionSynchronizationManager start!---------------");
        if (eventTransaction.isEmpty()) {
            TransactionSynchronizationManager.registerSynchronization(new EventsTransactionSynchronization());
        }
    }

    public class EventsTransactionSynchronization implements TransactionSynchronization{

        @Override
        public void afterCommit() {
            log.info("Transaction Success!!");
//            final List<Post> collect = Events.getEvents().stream().map(e -> (Post) e).collect(Collectors.toList());
//            System.out.println("post : " + collect);
            // todo Event 채널 만들어서 Event 채널로 보내기
            Events.getEvents().stream()
                    .map(e -> (Comment)e)
                    .forEach(post -> {
                        System.out.println(post.getAuthor());
                        System.out.println(post.getContent());
                    });

            Events.clear();
        }
    }
}