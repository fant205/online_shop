package com.alexey.shop.core.services;

import com.alexey.shop.core.SpringBootTestBase;
import com.alexey.shop.core.api.CartDto;
import com.alexey.shop.core.api.CartItemDto;
import com.alexey.shop.core.integration.CartServiceIntegration;
import com.alexey.shop.core.model.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

public class OrderServiceTest extends SpringBootTestBase {

    @Autowired
    OrdersService ordersService;
    @MockBean
    CartServiceIntegration cartServiceIntegration;


    @Test
    void createOrder() {

        CartDto cartDto = new CartDto();
        cartDto.setTotalPrice(200);
        List<CartItemDto> list = List.of(
                new CartItemDto(1L, "Test1", 1, 1, 1),
                new CartItemDto(1L, "Test1", 1, 1, 1),
                new CartItemDto(1L, "Test1", 1, 1, 1));
        cartDto.setItems(list);
        Mockito.when(cartServiceIntegration.getCurrentCart()).thenReturn(cartDto);

        Order bob = ordersService.createOrder("Bob");

        Optional<Order> optional = ordersService.findById(bob.getId());
        Assertions.assertFalse(optional.isEmpty());
        Order order = optional.get();
        Assertions.assertEquals(bob.getUsername(), order.getUsername());
        Assertions.assertEquals(bob.getUsername(), order.getUsername());
    }
}
