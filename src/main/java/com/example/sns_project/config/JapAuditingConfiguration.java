package com.example.sns_project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JapAuditingConfiguration { // todo Jpa에 의존되므로 엔티티마다 설정하기
}
