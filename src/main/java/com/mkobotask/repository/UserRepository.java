package com.mkobotask.repository;

import com.mkobotask.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Boolean existsByEmail(String email);
    Optional<User> findUserById(Long id);
    Optional<User> findUserByUserId( String userid);
}
