package com.uberApp.Uber.App.services;

import com.uberApp.Uber.App.entities.PaymentEntity;
import com.uberApp.Uber.App.entities.RideEntity;
import com.uberApp.Uber.App.entities.enums.PaymentStatus;

public interface PaymentService {
    void processPayment(RideEntity ride);

    PaymentEntity createNewPayment(RideEntity ride);

    void updatePaymentStatus(PaymentEntity payment, PaymentStatus status);

}
