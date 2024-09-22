package com.ecom.order_service.exceptions;

public class InsufficientStockException extends Throwable {
    public InsufficientStockException(String insufficientStock) {
        super(insufficientStock);
    }
}
