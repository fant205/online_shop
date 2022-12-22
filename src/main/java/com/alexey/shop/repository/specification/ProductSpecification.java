package com.alexey.shop.repository.specification;

import com.alexey.shop.model.Product;

import java.util.Locale;

public class ProductSpecification {

    public static org.springframework.data.jpa.domain.Specification<Product> equalId(Long id){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id));
    }

    public static org.springframework.data.jpa.domain.Specification<Product> likeTitle(String titlePart){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.upper(root.get("title")), String.format("%%%s%%", titlePart.toUpperCase())));
    }

    public static org.springframework.data.jpa.domain.Specification<Product> greaterThanOrEqualToScore(Integer cost){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), cost));
    }

    public static org.springframework.data.jpa.domain.Specification<Product> lessThanOrEqualToScore(Integer cost){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), cost));
    }

}
