package com.uberApp.Uber.App.services.impl;

import org.springframework.stereotype.Service;

import com.uberApp.Uber.App.entities.PaymentEntity;
import com.uberApp.Uber.App.entities.RideEntity;
import com.uberApp.Uber.App.entities.enums.PaymentStatus;
import com.uberApp.Uber.App.exceptions.ResourceNotFoundException;
import com.uberApp.Uber.App.repositories.PaymentRepository;
import com.uberApp.Uber.App.services.PaymentService;
import com.uberApp.Uber.App.strategies.PaymentStrategyManager;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentStrategyManager paymentStrategyManager;

    @Override
    public void processPayment(RideEntity ride) {
        PaymentEntity payment = paymentRepository.findByRide(ride).orElseThrow(
                () -> new ResourceNotFoundException("Payment not found for ride with id : " + ride.getId()));
        paymentStrategyManager.paymentStrategy(payment.getPaymentMethod()).processPayment(payment);
    }

    @Override
    public PaymentEntity createNewPayment(RideEntity ride) {
        PaymentEntity payment = PaymentEntity.builder().paymentMethod(ride.getPaymentMethod()).ride(ride)
                .amount(ride.getFare())
                .paymentStatus(PaymentStatus.PENDING).build();
        return paymentRepository.save(payment);

    }

    @Override
    public void updatePaymentStatus(PaymentEntity payment, PaymentStatus status) {
        payment.setPaymentStatus(status);
        paymentRepository.save(payment);
    }

}
