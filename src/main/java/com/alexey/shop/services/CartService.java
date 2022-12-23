package com.alexey.shop.services;

import com.alexey.shop.dto.Cart;
import com.alexey.shop.exceptions.ResourceNotFoundException;
import com.alexey.shop.model.Product;
import com.alexey.shop.validators.CartValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductsService productsService;
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
        Product product = productsService.findProductById(productId).orElseThrow(() -> new ResourceNotFoundException("Продукт не может быть добавлен в корзину. Продукт не найден по id: " + productId));
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
