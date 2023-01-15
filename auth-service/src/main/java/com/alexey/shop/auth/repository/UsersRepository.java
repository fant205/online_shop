package com.alexey.shop.auth.repository;

import com.alexey.shop.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {
    public Optional<User> findByUsername(String username);
}
