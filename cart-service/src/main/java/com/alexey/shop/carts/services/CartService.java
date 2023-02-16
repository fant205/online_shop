package com.alexey.shop.carts.services;

import com.alexey.shop.carts.exceptions.ValidationException;
import com.alexey.shop.core.api.ProductDto;
import com.alexey.shop.carts.integrations.ProductServiceIntegration;
import com.alexey.shop.carts.model.Cart;
import com.alexey.shop.carts.validators.CartValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductServiceIntegration productsServiceIntegration;
    private final RedisTemplate<String, Object> redisTemplate;
    @Value("{cart-service.cart-prefix}")
    private String cartPrefix;
    private final CartValidator cartValidator;
    private Map<String, Cart> map;

    @PostConstruct
    public void init() {

    }

    public Cart getCurrentCart(String username) {
        String key = cartPrefix + username;
        if (!redisTemplate.hasKey(key)) {
            redisTemplate.opsForValue().set(key, new Cart());
        }
        return (Cart) redisTemplate.opsForValue().get(key);
    }

    public void add(Long productId, String username) {
        ProductDto product = productsServiceIntegration.getProductById(productId);
        execute(username, cart -> cart.add(product));
    }

    public void increment(Long productId, Integer count, String username) {
//        map.get(username).increment(productId, count);
        execute(username, cart -> cart.increment(productId, count));


    }

    public void delete(Long id, String username) {
        execute(username, cart -> cart.delete(id));
    }

    public void clear(String username) {
        execute(username, Cart::clear);
    }

    private void execute(String uuid, Consumer<Cart> operation) {
        Cart cart = getCurrentCart(uuid);
        operation.accept(cart);
        redisTemplate.opsForValue().set(cartPrefix + uuid, cart);
    }

    public void merge(String username) {
        if(!StringUtils.hasText(username)){
            throw new ValidationException(List.of("username не может быть пустым!"));
        }
        if(username.toLowerCase().equals("guest")){
            throw new ValidationException(List.of("username не может guest!"));
        }

        Cart cart = getCurrentCart(username);
        Cart guestCart = getCurrentCart("guest");

        guestCart.getItems().forEach(cartItem -> {
            cart.add(cartItem);
        });

        redisTemplate.opsForValue().set(cartPrefix + username, cart);

    }
}