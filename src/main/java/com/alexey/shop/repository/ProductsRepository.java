package com.alexey.shop.repository;

import com.alexey.shop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ProductsRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    public Optional<Product> findByTitle(String title);

}
