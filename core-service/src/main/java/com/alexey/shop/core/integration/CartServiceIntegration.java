package com.alexey.shop.core.integration;

import com.alexey.shop.core.api.CartDto;
import com.alexey.shop.core.api.ProductDto;
import com.alexey.shop.core.api.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CartServiceIntegration {

    private final WebClient cartServiceWebClient;


    public CartDto getCurrentCart(String username) {
        return cartServiceWebClient.get()
                .uri("/api/v1/cart/")
                .header("username", username)
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.value() == HttpStatus.NO_CONTENT.value(),
                        clientResponse -> Mono.error(new ResourceNotFoundException("Товар не найден в МС!"))
                )
                .bodyToMono(CartDto.class)
                .block();
    }

    public void clear() {
        cartServiceWebClient.get()
                .uri("/api/v1/cart/clear")
                .retrieve()
                .toBodilessEntity()
                .block();
    }

}
