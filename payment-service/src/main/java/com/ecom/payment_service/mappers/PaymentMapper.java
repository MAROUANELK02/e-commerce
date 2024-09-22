package com.ecom.payment_service.mappers;

import com.ecom.payment_service.dtos.PaymentDTO;
import com.ecom.payment_service.entities.Payment;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class PaymentMapper {
    public Payment toPayment(PaymentDTO paymentCreated) {
        Payment payment = new Payment();
        BeanUtils.copyProperties(paymentCreated, payment);
        return payment;
    }

    public PaymentDTO toPaymentCreated(Payment payment) {
        PaymentDTO paymentCreated = new PaymentDTO();
        BeanUtils.copyProperties(payment, paymentCreated);
        return paymentCreated;
    }
}
