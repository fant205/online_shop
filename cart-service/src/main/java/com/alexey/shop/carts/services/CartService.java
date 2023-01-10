package com.alexey.shop.carts.services;

import com.alexey.shop.core.api.ProductDto;
import com.alexey.shop.core.api.ResourceNotFoundException;
import com.alexey.shop.carts.integrations.ProductServiceIntegration;
import com.alexey.shop.carts.model.Cart;
import com.alexey.shop.carts.validators.CartValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductServiceIntegration productsServiceIntegration;
    private final CartValidator cartValidator;
    private Cart tempCart;

    @PostConstruct
    public void init() {
        tempCart = new Cart();
    }

    public Cart getCurrentCart() {
        return tempCart;
    }

    public void add(Long productId) {
        ProductDto product = productsServiceIntegration.getProductById(productId).orElseThrow(() -> new ResourceNotFoundException("Продукт не может быть добавлен в корзину. Продукт не найден по id: " + productId));
        tempCart.add(product);
    }

    public void clear() {
        tempCart.clear();
    }

    public void increment(Long productId, Integer count) {
        tempCart.increment(productId, count);
    }

    public void delete(Long id) {
        tempCart.delete(id);
    }

}
