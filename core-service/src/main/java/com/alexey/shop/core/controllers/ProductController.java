package com.alexey.shop.core.controllers;

import com.alexey.shop.core.api.ProductDto;
import com.alexey.shop.core.api.ProductsGetDto;
import com.alexey.shop.core.api.ResourceNotFoundException;
import com.alexey.shop.core.mapper.ProductMapper;
import com.alexey.shop.core.model.Product;
import com.alexey.shop.core.services.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductsService productService;

    @GetMapping("/{id}")
//    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ProductDto findUsersByProductId(@PathVariable Long id) {
        Product product = productService.findProductById(id).orElseThrow(() -> new ResourceNotFoundException("Продукт не найден по id: " + id));
        return ProductMapper.MAPPER.fromProduct(product);
    }

    @GetMapping
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ProductsGetDto getAllProducts(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer min,
            @RequestParam(required = false) Integer max,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size) {
        Page<Product> products = productService.findAllProducts(id, title, min, max, page, size);
        List<ProductDto> list = ProductMapper.MAPPER.fromProductList(products.toList());
        return new ProductsGetDto(list, products.getTotalPages(), products.getTotalElements());
    }

    @PostMapping
    @Secured({"ROLE_USER"})
    public void create(@RequestBody ProductDto productDTO) {
        productService.create(productDTO);
    }

    @PutMapping
    @Secured({"ROLE_USER"})
    public void update(@RequestBody ProductDto productDto) {
        productService.update(productDto);
    }

    @DeleteMapping("/{id}")
    @Secured({"ROLE_USER"})
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

}