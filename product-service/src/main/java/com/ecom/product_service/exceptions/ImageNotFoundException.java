package com.ecom.product_service.exceptions;

public class ImageNotFoundException extends Exception {
    public ImageNotFoundException(String imageNotFound) {
        super(imageNotFound);
    }
}
