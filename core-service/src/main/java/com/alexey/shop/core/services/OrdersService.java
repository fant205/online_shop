package com.alexey.shop.core.services;

import com.alexey.shop.core.api.CartDto;
import com.alexey.shop.core.api.ProductNotFoundException;
import com.alexey.shop.core.integration.CartServiceIntegration;
import com.alexey.shop.core.model.Order;
import com.alexey.shop.core.model.OrderItem;
import com.alexey.shop.core.model.Product;
import com.alexey.shop.core.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrdersService {
    private final CartServiceIntegration cartServiceIntegration;
    private final ProductsService productsService;
    private final OrderRepository orderRepository;

    @Transactional
    public Order createOrder(String username) {
        CartDto cart = cartServiceIntegration.getCurrentCart(username);
        Order order = Order.builder()
                .totalPrice(cart.getTotalPrice())
                .username(username)
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
        Order save = orderRepository.save(order);
        cartServiceIntegration.clear();
        return save;
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> findByUsername(String username) {
        return orderRepository.findByUsername(username);
    }
}
