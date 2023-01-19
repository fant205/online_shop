package com.alexey.shop.core.controllers;

import com.alexey.shop.core.SpringBootTestBase;
import com.alexey.shop.core.api.ProductDto;
import com.alexey.shop.core.api.ProductsGetDto;
import com.alexey.shop.core.model.Product;
import com.alexey.shop.core.repository.ProductsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.NoSuchElementException;
import java.util.Optional;


public class ProductsControllerTest extends SpringBootTestBase {

    @Autowired
    private ProductsRepository productsRepository;
    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testFindAll() {

        ProductsGetDto responseBody = webTestClient.get()
                .uri("/api/v1/products")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProductsGetDto.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(3, responseBody.getList().size());
        Assertions.assertEquals(1, responseBody.getPagesCount());
        Assertions.assertEquals(3, responseBody.getRecordsTotal());
    }

    @Test
    public void testFind() {
        Product p = Product.builder()
                .title("testFind")
                .price(50)
                .build();
        final Product save = productsRepository.save(p);


        ProductsGetDto responseBody = webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/v1/products")
                        .queryParam("id", p.getId())
                        .queryParam("title", p.getTitle())
                        .queryParam("min", p.getPrice())
                        .queryParam("max", p.getPrice())
                        .queryParam("page", 0)
                        .queryParam("size", 1)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProductsGetDto.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(1, responseBody.getList().size());
        Assertions.assertEquals(1, responseBody.getPagesCount());
        Assertions.assertEquals(1, responseBody.getRecordsTotal());
        ProductDto productDto = responseBody.getList().get(0);
        Assertions.assertEquals(p.getId(), productDto.getId());
        Assertions.assertEquals(p.getTitle(), productDto.getTitle());
        Assertions.assertEquals(p.getPrice(), productDto.getPrice());
    }

    @Test
    public void testFindById() {
        Product p = Product.builder()
                .title("testFindById")
                .price(50)
                .build();
        p = productsRepository.save(p);

        Product responseBody = webTestClient.get()
                .uri("/api/v1/products/" + p.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Product.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(p.getId(), responseBody.getId());
        Assertions.assertEquals(p.getTitle(), responseBody.getTitle());
        Assertions.assertEquals(p.getPrice(), responseBody.getPrice());
    }

    @Test
    public void testFindByIdNotFound() {
        webTestClient.get()
                .uri("/api/v1/products/-1")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void testCreate() {
        Product butter = Product.builder()
                .title("Butter")
                .price(50)
                .build();

        webTestClient.post()
                .uri("/api/v1/products")
                .syncBody(butter)
                .exchange();

        Optional<Product> o = productsRepository.findByTitle("Butter");
        Assertions.assertNotNull(o.get());
        Product product = o.get();
        Assertions.assertEquals("Butter", product.getTitle());
        Assertions.assertEquals(50, product.getPrice());

    }

    @Test
    public void testUpdate() {
        Product p = Product.builder()
                .title("testCreate")
                .price(50)
                .build();
        p = productsRepository.save(p);
        p.setTitle(p.getTitle() + "_2");

        webTestClient.put()
                .uri("/api/v1/products")
                .syncBody(p)
                .exchange();

        Optional<Product> o = productsRepository.findById(p.getId());
        Assertions.assertNotNull(o.get());
        Product product = o.get();
        Assertions.assertEquals("testCreate_2", product.getTitle());
        Assertions.assertEquals(50, product.getPrice());

    }

    @Test
    public void testDelete() {
        Product p = Product.builder()
                .title("testCreate")
                .price(50)
                .build();
        p = productsRepository.save(p);
        final Long id = p.getId();

        webTestClient.delete()
                .uri("/api/v1/products/" + p.getId())
                .exchange();

        Optional<Product> o = productsRepository.findById(id);

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> o.get())
                .isInstanceOf(NoSuchElementException.class);

    }
}
