package com.alexey.shop.controllers;

import com.alexey.shop.exceptions.UserNotFoundException;
import com.alexey.shop.services.UserService;
import com.alexey.shop.services.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrdersService ordersService;
    private final UserService userService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(Principal principal) {
        userService.findByUsername(principal.getName()).orElseThrow(() -> new UserNotFoundException(String.format("Пользователя с именем не существует!", principal.getName())));
        ordersService.createOrder(principal.getName());
    }

}
