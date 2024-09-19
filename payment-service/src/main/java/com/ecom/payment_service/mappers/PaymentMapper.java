package com.ecom.payment_service.mappers;

import com.ecom.payment_service.dtos.PaymentCreated;
import com.ecom.payment_service.entities.Payment;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class PaymentMapper {
    public Payment toPayment(PaymentCreated paymentCreated) {
        Payment payment = new Payment();
        BeanUtils.copyProperties(paymentCreated, payment);
        return payment;
    }

    public PaymentCreated toPaymentCreated(Payment payment) {
        PaymentCreated paymentCreated = new PaymentCreated();
        BeanUtils.copyProperties(payment, paymentCreated);
        return paymentCreated;
    }
}
