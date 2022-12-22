package com.alexey.shop.controllers;

import com.alexey.shop.dto.Cart;
import com.alexey.shop.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/add/{id}")
    public void addToCart(@PathVariable Long id) {
        cartService.add(id);
    }

    @GetMapping
    public Cart getCurrentCart() {
        return cartService.getCurrentCart();
    }

    @GetMapping("/clear")
    public void clear() {
        cartService.clear();
    }

    @GetMapping("/increment/{id}")
    public void increment(@PathVariable Long id, @RequestParam(name = "count") Integer count) {
        cartService.increment(id, count);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        cartService.delete(id);
    }

}