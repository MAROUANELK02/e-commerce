package com.ecom.product_service.exceptions;

public class ProductNotFoundException extends Exception {
    public ProductNotFoundException(String productNotFound) {
        super(productNotFound);
    }
}
