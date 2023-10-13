package com.example.sns.infra.jpa;

import com.example.sns.domain.user.UserRepository;
import com.example.sns.domain.user.entity.User;
import com.example.sns.domain.user.entity.UserId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<User> findById(final UserId id) {
        try {
            return Optional.of(entityManager.find(User.class, id));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByEmail(final String email) {
        final User findUser;

        try {
            findUser = entityManager.createQuery("select u from User u where u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return Optional.of(findUser);
        } catch (Exception e) {
            return Optional.empty();
        }

    }

    @Override
    public void save(final User user) {
        entityManager.persist(user);
    }

    @Override
    public void deleteAll() {
        entityManager.createQuery("delete from User");
    }

    @Override
    public int count() {
        return entityManager.createQuery("select u from User u", User.class)
                .getResultList()
                .size();
    }
}
