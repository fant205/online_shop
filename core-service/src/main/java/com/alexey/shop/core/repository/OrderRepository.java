package com.alexey.shop.core.repository;

import com.alexey.shop.core.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    public List<Order> findByUsername(String username);

}
