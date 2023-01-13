package com.alexey.shop.core.services;

import com.alexey.shop.core.api.CartDto;
import com.alexey.shop.core.api.ProductNotFoundException;
import com.alexey.shop.core.api.UserNotFoundException;
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

@Service
@RequiredArgsConstructor
public class OrdersService {
    private final CartServiceIntegration cartServiceIntegration;
    private final ProductsService productsService;
    private final OrderRepository orderRepository;

    @Transactional
    public void createOrder(String username) {
        CartDto cart = cartServiceIntegration.getCurrentCart();
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
        orderRepository.save(order);
        cartServiceIntegration.clear();
    }
}
