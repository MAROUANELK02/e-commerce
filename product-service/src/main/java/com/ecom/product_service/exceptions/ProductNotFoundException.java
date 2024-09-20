package com.ecom.product_service.exceptions;

public class ProductNotFoundException extends Throwable {
    public ProductNotFoundException(String productNotFound) {
        super(productNotFound);
    }
}
