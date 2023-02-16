package com.alexey.shop.core.mapper;

import com.alexey.shop.core.api.OrderDto;
import com.alexey.shop.core.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderConverter {
    private final OrderItemConverter orderItemConverter;

    public OrderDto entityToDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setAddress(order.getAddress());
        orderDto.setItems(order.getItems().stream().map(orderItemConverter::entityToDto).collect(Collectors.toList()));
        orderDto.setPhone(order.getPhone());
        orderDto.setUsername(order.getUsername());
        orderDto.setTotalPrice(order.getTotalPrice());
        return orderDto;
    }
}
