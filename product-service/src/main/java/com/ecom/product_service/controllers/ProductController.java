package com.ecom.product_service.controllers;

import com.ecom.product_service.dtos.ProductDTO;
import com.ecom.product_service.entities.Product;
import com.ecom.product_service.enums.ECategory;
import com.ecom.product_service.exceptions.ProductNotFoundException;
import com.ecom.product_service.mappers.ProductMapper;
import com.ecom.product_service.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping("/products/all")
    public ResponseEntity<?> allProducts(@RequestParam(required = false, defaultValue = "0") int page,
                                         @RequestParam(required = false, defaultValue = "8") int size) {
        try {
            Page<Product> products = productService.getProducts(page, size);
            List<ProductDTO> productDTOS = products.getContent().stream()
                    .map(productMapper::ToProductDTO).toList();
            Page<ProductDTO> productDTOPage = new PageImpl<>(productDTOS,
                    products.getPageable(), products.getTotalElements());
            return ResponseEntity.ok(productDTOPage);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/saveProduct")
    public ResponseEntity<?> saveProduct(@RequestBody ProductDTO product) {
        try {
            Product added = productService.addProduct(productMapper.ToProduct(product));
            return ResponseEntity.ok(added);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProduct(@PathVariable(name = "id") Long id) {
        try {
            Product product = productService.getProduct(id);
            return ResponseEntity.ok(product);
        } catch (ProductNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/products/category/{categoryName}")
    public ResponseEntity<?> getAllProductsByCategory(@PathVariable(name = "categoryName") ECategory categoryName,
                                            @RequestParam(required = false, defaultValue = "0") int page,
                                            @RequestParam(required = false, defaultValue = "8") int size) {
        try {
            Page<Product> productsByCategory = productService.getProductsByCategory(categoryName,
                    page, size);
            List<ProductDTO> productDTOS = productsByCategory.getContent().stream()
                    .map(productMapper::ToProductDTO).toList();
            PageImpl<ProductDTO> products = new PageImpl<>(productDTOS, productsByCategory.getPageable(),
                    productsByCategory.getTotalElements());
            return ResponseEntity.ok(products);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/products/name/{productName}")
    public ResponseEntity<?> getAllProductsByName(@PathVariable(name = "productName") String name,
                                            @RequestParam(required = false, defaultValue = "0") int page,
                                            @RequestParam(required = false, defaultValue = "8") int size) {
        try {
            Page<Product> productsByCategory = productService.getProductsByName(name, page, size);
            List<ProductDTO> productDTOS = productsByCategory.getContent().stream()
                    .map(productMapper::ToProductDTO).toList();
            PageImpl<ProductDTO> products = new PageImpl<>(productDTOS, productsByCategory.getPageable(),
                    productsByCategory.getTotalElements());
            return ResponseEntity.ok(products);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/products/priceLessThan/{price}")
    public ResponseEntity<?> getAllProductsByPriceLessThan(@PathVariable(name = "price") BigDecimal price,
                                                  @RequestParam(required = false, defaultValue = "0") int page,
                                                  @RequestParam(required = false, defaultValue = "8") int size) {
        try {
            Page<Product> productsByCategory = productService.getProductsByPriceLessThan(price, page, size);
            List<ProductDTO> productDTOS = productsByCategory.getContent().stream()
                    .map(productMapper::ToProductDTO).toList();
            PageImpl<ProductDTO> products = new PageImpl<>(productDTOS, productsByCategory.getPageable(),
                    productsByCategory.getTotalElements());
            return ResponseEntity.ok(products);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/products/priceGreaterThan/{price}")
    public ResponseEntity<?> getProductsByPriceGreaterThan(@PathVariable(name = "price") BigDecimal price,
                                                           @RequestParam(required = false, defaultValue = "0") int page,
                                                           @RequestParam(required = false, defaultValue = "8") int size) {
        try {
            Page<Product> productsByCategory = productService.getProductsByPriceGreaterThan(price, page, size);
            List<ProductDTO> productDTOS = productsByCategory.getContent().stream()
                    .map(productMapper::ToProductDTO).toList();
            PageImpl<ProductDTO> products = new PageImpl<>(productDTOS, productsByCategory.getPageable(),
                    productsByCategory.getTotalElements());
            return ResponseEntity.ok(products);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable(name = "id") Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok().body("User deleted successfully");
        } catch (ProductNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
