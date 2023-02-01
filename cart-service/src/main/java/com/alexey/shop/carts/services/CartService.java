package com.alexey.shop.carts.services;

import com.alexey.shop.core.api.ProductDto;
import com.alexey.shop.carts.integrations.ProductServiceIntegration;
import com.alexey.shop.carts.model.Cart;
import com.alexey.shop.carts.validators.CartValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductServiceIntegration productsServiceIntegration;
    private final CartValidator cartValidator;
    //    private Cart tempCart;
    private Map<String, Cart> map;

    @PostConstruct
    public void init() {
//        tempCart = new Cart();
        map = new HashMap<>();
    }

    public Cart getCurrentCart(String username) {
        Cart cart = map.get(username);
        if(cart == null){
            cart = new Cart();
            map.put(username, cart);
        }
        return cart;
    }

    public void add(Long productId, String username) {
        ProductDto product = productsServiceIntegration.getProductById(productId);
        Cart cart = map.get(username);
        if(cart == null){
            cart = new Cart();
            map.put(username, cart);
        }
        cart.add(product);
    }

    public void clear(String username) {
        map.get(username).clear();
    }

    public void increment(Long productId, Integer count, String username) {
        map.get(username).increment(productId, count);
    }

    public void delete(Long id, String username) {
        map.get(username).delete(id);
    }

}
