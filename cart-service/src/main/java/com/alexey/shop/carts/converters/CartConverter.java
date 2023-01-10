package com.alexey.shop.carts.converters;

import com.alexey.shop.core.api.CartDto;
import com.alexey.shop.carts.model.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CartConverter {

    private final CartItemConverter cartItemConverter;

    public CartDto entityToDto(Cart entity) {
        CartDto dto = new CartDto();
        dto.setTotalPrice(entity.getTotalPrice());
        dto.setItems(entity.getItems().stream().map(cartItemConverter::entityToDto).collect(Collectors.toList()));
        return dto;

    }

}
