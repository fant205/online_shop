package com.alexey.shop.core.mapper;

import com.alexey.shop.core.api.OrderItemDto;
import com.alexey.shop.core.model.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderItemConverter {

    public OrderItemDto entityToDto(OrderItem orderItem) {
        OrderItemDto dto = new OrderItemDto();
        dto.setId(orderItem.getId());
        dto.setPrice(orderItem.getPrice());
        dto.setQuantity(orderItem.getQuantity());
        dto.setPricePerProduct(orderItem.getPricePerProduct());
        dto.setProductTitle(orderItem.getProduct().getTitle());
        return dto;
    }
}
