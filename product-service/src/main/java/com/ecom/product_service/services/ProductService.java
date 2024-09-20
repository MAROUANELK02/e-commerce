package com.ecom.product_service.services;

import com.ecom.product_service.entities.Product;
import com.ecom.product_service.enums.ECategory;
import com.ecom.product_service.exceptions.ProductNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface ProductService {
    Product addProduct(Product product);
    Page<Product> getProducts(int page, int size);
    Product getProduct(Long id) throws ProductNotFoundException;
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id) throws ProductNotFoundException;
    Page<Product> getProductsByCategory(ECategory category, int page, int size);
    Page<Product> getProductsByName(String name, int page, int size);
    Page<Product> getProductsByPriceLessThan(BigDecimal price, int page, int size);
    Page<Product> getProductsByPriceGreaterThan(BigDecimal price, int page, int size);
}
