package com.alexey.shop.core.repository;

import com.alexey.shop.core.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {
    public Optional<User> findByUsername(String username);
}
