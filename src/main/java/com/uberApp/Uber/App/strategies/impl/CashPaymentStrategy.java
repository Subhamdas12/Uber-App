package com.uberApp.Uber.App.strategies.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uberApp.Uber.App.entities.DriverEntity;
import com.uberApp.Uber.App.entities.PaymentEntity;
import com.uberApp.Uber.App.entities.enums.PaymentStatus;
import com.uberApp.Uber.App.entities.enums.TransactionMethod;
import com.uberApp.Uber.App.repositories.PaymentRepository;
import com.uberApp.Uber.App.services.PaymentService;
import com.uberApp.Uber.App.services.WalletService;
import com.uberApp.Uber.App.strategies.PaymentStrategy;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CashPaymentStrategy implements PaymentStrategy {

    private final WalletService walletService;
    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public void processPayment(PaymentEntity payment) {
        DriverEntity driver = payment.getRide().getDriver();
        double platform_commision = payment.getAmount() * PLATFORM_COMMISION;
        walletService.deductMoneyFromWallet(driver.getUser(), platform_commision, null, payment.getRide(),
                TransactionMethod.RIDE);

        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);
    }

}
