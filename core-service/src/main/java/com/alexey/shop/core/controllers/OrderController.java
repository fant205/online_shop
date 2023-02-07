package com.alexey.shop.core.controllers;

import com.alexey.shop.core.api.OrderDto;
import com.alexey.shop.core.mapper.OrderConverter;
import com.alexey.shop.core.mapper.OrderItemConverter;
import com.alexey.shop.core.model.Order;
import com.alexey.shop.core.services.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrdersService ordersService;
    private final OrderConverter orderConverter;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestHeader(defaultValue = "guest") String username) {
        ordersService.createOrder(username);
    }

    @GetMapping
    public List<OrderDto> orders(@RequestHeader(defaultValue = "guest") String username) {
        return ordersService.findByUsername(username).stream().map(orderConverter::entityToDto).collect(Collectors.toList());
    }

}
