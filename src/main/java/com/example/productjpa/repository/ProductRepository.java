package com.example.productjpa.repository;

import com.example.productjpa.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContainingIgnoreCase(String name);

    Product findByName(String name);

    List<Product> findByDetailsContainingIgnoreCase(String details);

    List<Product> findByPriceBetween(double min, double max);

    List<Product> findByStockGreaterThan(int stock);

    List<Product> findAllByOrderByPriceAsc();

    List<Product> findAllByOrderByPriceDesc();
}
