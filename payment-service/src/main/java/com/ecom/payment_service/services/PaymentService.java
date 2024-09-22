package com.ecom.payment_service.services;

import com.ecom.payment_service.entities.Payment;
import com.ecom.payment_service.enums.PaymentStatus;
import com.ecom.payment_service.exceptions.PaymentNotFoundException;

public interface PaymentService {
    Payment getPaymentByUserId(long userId) throws PaymentNotFoundException;
    PaymentStatus getPaymentStatusByUserId(long userId) throws PaymentNotFoundException;
    String getRedirectUrlByUserId(long userId) throws PaymentNotFoundException;
    void confirmPaymentByUserId(long userId) throws PaymentNotFoundException;
}
