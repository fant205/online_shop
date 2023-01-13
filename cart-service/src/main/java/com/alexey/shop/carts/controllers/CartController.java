package com.alexey.shop.carts.controllers;

import com.alexey.shop.core.api.CartDto;
import com.alexey.shop.carts.converters.CartConverter;
import com.alexey.shop.carts.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CartController {

    private final CartService cartService;
    private final CartConverter cartConverter;

    @GetMapping("/add/{id}")
    public void addToCart(@PathVariable Long id) {
        cartService.add(id);
    }

    @GetMapping
    public CartDto getCurrentCart() {
        return cartConverter.entityToDto(cartService.getCurrentCart());
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