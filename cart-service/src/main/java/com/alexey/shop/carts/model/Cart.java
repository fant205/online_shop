package com.alexey.shop.carts.model;


import com.alexey.shop.core.api.ProductDto;
import lombok.Data;

import java.util.*;

@Data
public class Cart {


    private List<CartItem> items;
    private int totalPrice;

    public Cart() {
        items = new ArrayList<>();
    }

    public void add(ProductDto product) {
        CartItem item = findCartItem(product.getId());
        if (item == null) {
            items.add(new CartItem(product.getId(), product.getTitle(), 1, product.getPrice(), product.getPrice()));
        } else {
            item.incrementQuantity();
        }
        recalculate();
    }

    public void add(CartItem product) {
        CartItem item = findCartItem(product.getProductId());
        if (item == null) {
            items.add(new CartItem(product.getProductId(), product.getProductTitle(), 1, product.getPrice(), product.getPrice()));
        } else {
            item.incrementQuantity();
        }
        recalculate();
    }

    private CartItem findCartItem(Long productId) {
        for (CartItem item : items) {
            if (item.getProductId().longValue() == productId.longValue()) {
                return item;
            }
        }
        return null;
    }

    private void recalculate() {
        totalPrice = 0;
        for (CartItem item : items) {
            totalPrice += item.getPricePerProduct() * item.getQuantity();
        }
    }

    public void clear() {
        items.clear();
        totalPrice = 0;
    }

    public void increment(Long productId, Integer count) {
        CartItem cartItem = findCartItem(productId);
        cartItem.setQuantity(cartItem.getQuantity() + count);
        cartItem.setPrice(cartItem.getPricePerProduct() * cartItem.getQuantity());
        recalculate();
    }

    public void delete(Long id) {
        CartItem cartItem = items.stream().filter(o -> o.getProductId().longValue() == id.longValue()).findFirst().get();
        totalPrice = totalPrice - (cartItem.getPricePerProduct() * cartItem.getQuantity());
        items.removeIf(o -> o.getProductId().longValue() == id.longValue());
    }
}
