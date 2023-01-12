package com.alexey.shop.core.api;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String msg){
        super(msg);
    }
}
