package com.alexey.shop.core.model;

import lombok.Data;

@Data
public class Item {
    private Product product;
    private Integer count;
}