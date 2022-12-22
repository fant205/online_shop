package com.alexey.shop.model;

import lombok.Data;

@Data
public class Item {
    private Product product;
    private Integer count;
}