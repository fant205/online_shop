package com.alexey.shop.carts.converters;

import com.alexey.shop.core.api.CartItemDto;
import com.alexey.shop.carts.model.CartItem;
import org.springframework.stereotype.Component;

@Component
public class CartItemConverter {

    public CartItemDto entityToDto(CartItem entity) {
        CartItemDto dto = new CartItemDto();
        dto.setProductId(entity.getProductId());
        dto.setQuantity(entity.getQuantity());
        dto.setProductTitle(entity.getProductTitle());
        dto.setPrice(entity.getPrice());
        dto.setPricePerProduct(entity.getPricePerProduct());
        return dto;
    }
}
