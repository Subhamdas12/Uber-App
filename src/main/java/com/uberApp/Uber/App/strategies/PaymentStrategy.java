package com.uberApp.Uber.App.strategies;

import com.uberApp.Uber.App.entities.PaymentEntity;

public interface PaymentStrategy {
    Double PLATFORM_COMMISION = 0.3;

    public void processPayment(PaymentEntity payment);
}
