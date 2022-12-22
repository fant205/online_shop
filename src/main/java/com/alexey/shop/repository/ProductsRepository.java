package com.alexey.shop.repository;

import com.alexey.shop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductsRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

}
