package com.ecom.payment_service.services;

import com.ecom.payment_service.entities.Payment;
import com.ecom.payment_service.enums.PaymentStatus;
import com.ecom.payment_service.exceptions.PaymentNotFoundException;
import com.ecom.payment_service.repositories.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    @Override
    public Payment getPaymentByOrderId(long orderId) throws PaymentNotFoundException {
        Optional<Payment> payment = paymentRepository.findByOrderId(orderId);
        if(payment.isPresent()) {
            return payment.get();
        }else{
            throw new PaymentNotFoundException("Payment not found");
        }
    }

    @Override
    public PaymentStatus getPaymentStatusByOrderId(long orderId) throws PaymentNotFoundException {
        return getPaymentByOrderId(orderId).getStatus();
    }

    @Override
    public String getRedirectUrlByOrderId(long orderId) throws PaymentNotFoundException {
        return getPaymentByOrderId(orderId).getRedirectUrl();
    }

    @Override
    public void confirmPaymentByOrderId(long orderId) throws PaymentNotFoundException {
        Payment payment = getPaymentByOrderId(orderId);
        payment.setStatus(PaymentStatus.COMPLETED);
        paymentRepository.save(payment);
    }
}
