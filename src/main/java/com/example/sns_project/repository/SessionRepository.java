package com.example.sns_project.repository;

import com.example.sns_project.domain.user.Session;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SessionRepository extends CrudRepository<Session, Long> {
    Optional<Session> findByAccessToken(String accessToken);
}
