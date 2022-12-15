package com.alexey.shop.dto;

import com.alexey.shop.model.Product;
import lombok.Data;

@Data
public class ItemDto {
    private Product product;
    private Integer count;
}
