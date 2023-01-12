package com.alexey.shop.core.controllers;

import com.alexey.shop.core.api.UserNotFoundException;
import com.alexey.shop.core.services.OrdersService;
import com.alexey.shop.core.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@CrossOrigin("*")
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
