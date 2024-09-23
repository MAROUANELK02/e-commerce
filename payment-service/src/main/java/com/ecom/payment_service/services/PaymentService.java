package com.ecom.payment_service.services;

import com.ecom.payment_service.entities.Payment;
import com.ecom.payment_service.enums.PaymentStatus;
import com.ecom.payment_service.exceptions.PaymentNotFoundException;

public interface PaymentService {
    Payment getPaymentByOrderId(long orderId) throws PaymentNotFoundException;
    PaymentStatus getPaymentStatusByOrderId(long orderId) throws PaymentNotFoundException;
    String getRedirectUrlByOrderId(long orderId) throws PaymentNotFoundException;
    void confirmPaymentByOrderId(long orderId) throws PaymentNotFoundException;
}
