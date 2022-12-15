package com.alexey.shop.controllers;

import com.alexey.shop.dto.ItemDto;
import com.alexey.shop.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public List<ItemDto> getAllItems() {
        return cartService.findAll();
    }

    @PostMapping
    public void post(@RequestBody ItemDto itemDto) {
        cartService.add(itemDto);
    }

    @PutMapping
    public void put(@RequestBody ItemDto itemDto) {
        cartService.modify(itemDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        cartService.delete(id);
    }

}