package com.alexey.shop.carts.controllers;

import com.alexey.shop.core.api.CartDto;
import com.alexey.shop.carts.converters.CartConverter;
import com.alexey.shop.carts.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final CartConverter cartConverter;

    @GetMapping("/add/{id}")
    public void addToCart(@PathVariable Long id, @RequestHeader(defaultValue = "guest") String username) {
        cartService.add(id, username);
    }

    @GetMapping
    public CartDto getCurrentCart(@RequestHeader(defaultValue = "guest") String username) {
        return cartConverter.entityToDto(cartService.getCurrentCart(username));
    }

    @GetMapping("/clear")
    public void clear(@RequestHeader(defaultValue = "guest") String username) {
        cartService.clear(username);
    }

    @GetMapping("/increment/{id}")
    public void increment(@PathVariable Long id, @RequestParam(name = "count") Integer count, @RequestHeader(defaultValue = "guest") String username) {
        cartService.increment(id, count, username);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, @RequestHeader(defaultValue = "guest") String username) {
        cartService.delete(id, username);
    }

}