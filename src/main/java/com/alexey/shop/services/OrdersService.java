package com.alexey.shop.services;

import com.alexey.shop.dto.Cart;
import com.alexey.shop.exceptions.ProductNotFoundException;
import com.alexey.shop.exceptions.UserNotFoundException;
import com.alexey.shop.model.Order;
import com.alexey.shop.model.OrderItem;
import com.alexey.shop.model.Product;
import com.alexey.shop.model.User;
import com.alexey.shop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrdersService {
    private final CartService cartService;
    private final UserService userService;
    private final ProductsService productsService;
    private final OrderRepository orderRepository;

    @Transactional
    public void createOrder(String name) {
        Cart cart = cartService.getCurrentCart();
        User user = userService.findByUsername(name).orElseThrow(() -> new UserNotFoundException(String.format("Пользователя с именем не существует!", name)));
        Order order = Order.builder()
                .totalPrice(cart.getTotalPrice())
                .user(user)
                .build();

        List<OrderItem> list = new ArrayList<>();
        cart.getItems().stream().forEach(o -> {
            Product product = productsService.findProductById(o.getProductId()).orElseThrow(() -> new ProductNotFoundException("Продукт не найден! id: " + o.getProductId()));
            OrderItem item = OrderItem.builder()
                    .product(product)
                    .order(order)
                    .quantity(o.getQuantity())
                    .price(o.getPrice())
                    .pricePerProduct(o.getPricePerProduct())
                    .build();
            list.add(item);
        });

        order.setItems(list);
        orderRepository.save(order);
    }
}
