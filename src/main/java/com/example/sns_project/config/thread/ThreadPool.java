package com.example.sns_project.config.thread;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * ThreadPoolTaskExecutor는 일반적으로 일정 시간 동안 아무런 작업을 처리하지 않는 스레드를 종료하여 스레드 풀의 리소스를 효율적으로 관리
 * setCorePoolSize ThreadPool의 기본 스레드 수 설정
 * setMaxPoolSize ThreadPool의 최대 스레드 수 설정
 * setKeepAliveSeconds 대기 중인 스레드(Idle Thread)의 최대 대기 시간을 설정 -> 지나면 사라짐
 * setQueueCapacity 대기 중인 작업 큐의 크기 설정
 */
@Configuration
public class ThreadPool {
    @Bean
    public Executor getDomainEventTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20);
        executor.setQueueCapacity(20);
        executor.setMaxPoolSize(100);
        executor.setKeepAliveSeconds(1);
        return executor;
    }
}
