package com.alexey.shop.core.repository;

import com.alexey.shop.core.model.Item;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CartRepository {

    private List<Item> cart;

    @PostConstruct
    public void init() {
        cart = new ArrayList<>();
    }

    public Optional<Item> findById(Long id) {
        return cart.stream().filter(o -> o.getProduct().getId().longValue() == id.longValue()).findFirst();
    }

    public List<Item> findAll() {
        return cart;
    }

    public void create(Item item) {
        cart.add(item);
    }

    public void update(Item item) {
        int i = cart.indexOf(item);
        cart.set(i, item);
    }

    public void delete(Long id) {
        cart.removeIf(o -> o.getProduct().getId().longValue() == id.longValue());
    }

}