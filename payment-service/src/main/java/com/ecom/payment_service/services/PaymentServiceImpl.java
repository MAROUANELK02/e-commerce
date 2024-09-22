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
    public Payment getPaymentByUserId(long userId) throws PaymentNotFoundException {
        Optional<Payment> payment = paymentRepository.findByUserId(userId);
        if(payment.isPresent()) {
            return payment.get();
        }else{
            throw new PaymentNotFoundException("Payment not found");
        }
    }

    @Override
    public PaymentStatus getPaymentStatusByUserId(long userId) throws PaymentNotFoundException {
        return getPaymentByUserId(userId).getStatus();
    }

    @Override
    public String getRedirectUrlByUserId(long userId) throws PaymentNotFoundException {
        return getPaymentByUserId(userId).getRedirectUrl();
    }

    @Override
    public void confirmPaymentByUserId(long userId) throws PaymentNotFoundException {
        Payment payment = getPaymentByUserId(userId);
        payment.setStatus(PaymentStatus.COMPLETED);
        paymentRepository.save(payment);
    }
}
