package com.example.sns_project.repository;

import com.example.sns_project.domain.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Long> {
    Optional<User> findByEmailAndPassword(String email, String password);
}
