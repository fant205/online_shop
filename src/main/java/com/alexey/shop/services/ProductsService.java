package com.alexey.shop.services;

import com.alexey.shop.dto.ProductDto;
import com.alexey.shop.mapper.ProductMapper;
import com.alexey.shop.model.Product;
import com.alexey.shop.repository.ProductsRepository;
import com.alexey.shop.repository.specification.ProductSpecification;
import com.alexey.shop.validators.ProductValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductsService {

    private final ProductsRepository productsRepository;
    private final ProductValidator productValidator;

    public Optional<Product> findProductById(Long id) {
        return productsRepository.findById(id);
    }

    public Page<Product> findAllProducts(Long id, String title, Integer min, Integer max, Integer page, Integer size) {
        Specification<Product> spec = Specification
                .where(id == null ? null : ProductSpecification.equalId(id))
                .and(StringUtils.hasText(title) ? ProductSpecification.likeTitle(title) : null)
                .and(min == null ? null : ProductSpecification.greaterThanOrEqualToScore(min))
                .and(max == null ? null : ProductSpecification.lessThanOrEqualToScore(max));

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id"));
        return productsRepository.findAll(spec, pageRequest);
    }

    @Transactional
    public void create(ProductDto productDTO) {
        productValidator.validate(productDTO);
        Product product = ProductMapper.MAPPER.toProduct(productDTO);
        productsRepository.save(product);
    }

    @Transactional
    public void update(ProductDto productDTO) {
        productValidator.validate(productDTO);
        Product product = productsRepository.findById(productDTO.getId()).orElseThrow();
        product.setTitle(productDTO.getTitle());
        product.setPrice(productDTO.getPrice());
        productsRepository.save(product);
    }

    @Transactional
    public void delete(Long id) {
        Product product = Product.builder().id(id).build();
        productsRepository.delete(product);
    }

}
