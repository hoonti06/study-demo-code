package com.spring.login.repository;

import com.spring.login.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
//public interface UserRepository extends MongoRepository<User, String> {
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
