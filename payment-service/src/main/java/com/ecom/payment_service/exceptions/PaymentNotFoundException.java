package com.ecom.payment_service.exceptions;

public class PaymentNotFoundException extends Throwable {
    public PaymentNotFoundException(String paymentNotFound) {
        super(paymentNotFound);
    }
}
