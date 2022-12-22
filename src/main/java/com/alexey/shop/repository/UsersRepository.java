package com.alexey.shop.repository;

import com.alexey.shop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {
    public Optional<User> findByUsername(String username);
}
