package com.example.sns_project.domain.user;

import com.example.sns_project.domain.user.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    void save(User user);
    void deleteAll();

    int count();
}
