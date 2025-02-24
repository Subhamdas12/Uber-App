package com.uberApp.Uber.App.strategies.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uberApp.Uber.App.entities.DriverEntity;
import com.uberApp.Uber.App.entities.PaymentEntity;
import com.uberApp.Uber.App.entities.RiderEntity;
import com.uberApp.Uber.App.entities.enums.PaymentStatus;
import com.uberApp.Uber.App.entities.enums.TransactionMethod;
import com.uberApp.Uber.App.repositories.PaymentRepository;
import com.uberApp.Uber.App.services.WalletService;
import com.uberApp.Uber.App.strategies.PaymentStrategy;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WalletPaymentStrategy implements PaymentStrategy {

    private final WalletService walletService;
    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public void processPayment(PaymentEntity payment) {

        DriverEntity driver = payment.getRide().getDriver();
        RiderEntity rider = payment.getRide().getRider();

        walletService.deductMoneyFromWallet(rider.getUser(), payment.getAmount(), null, payment.getRide(),
                TransactionMethod.RIDE);

        double driversCut = payment.getRide().getFare() * (1 - PLATFORM_COMMISION);
        walletService.addMoneyToWallet(driver.getUser(), driversCut, null, payment.getRide(),
                TransactionMethod.RIDE);

        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);
    }

}
