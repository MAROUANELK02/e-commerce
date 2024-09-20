package com.ecom.product_service.repositories;

import com.ecom.product_service.entities.Product;
import com.ecom.product_service.enums.ECategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByNameContainsIgnoreCase(String productName, Pageable pageable);
    Page<Product> findByCategoryCategoryName(ECategory category, Pageable pageable);
    Page<Product> findByPriceLessThanEqual(BigDecimal price, Pageable pageable);
    Page<Product> findByPriceGreaterThanEqual(BigDecimal price, Pageable pageable);
}
