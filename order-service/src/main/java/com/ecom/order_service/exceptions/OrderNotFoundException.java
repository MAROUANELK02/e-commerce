package com.ecom.order_service.exceptions;

public class OrderNotFoundException extends Throwable {
    public OrderNotFoundException(String orderNotFound) {
        super(orderNotFound);
    }
}
