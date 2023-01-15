package com.alexey.shop.carts.integrations;

import com.alexey.shop.core.api.ProductDto;
import com.alexey.shop.core.api.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductServiceIntegration {
    private final WebClient productServiceWebClient;


    public ProductDto getProductById(Long id) {
        return productServiceWebClient.get()
                .uri("/api/v1/products/" + id)
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.value() == HttpStatus.NO_CONTENT.value(),
                        clientResponse -> Mono.error(new ResourceNotFoundException("Товар не найден в МС!"))
                )
                .bodyToMono(ProductDto.class)
                .block();
    }
}
