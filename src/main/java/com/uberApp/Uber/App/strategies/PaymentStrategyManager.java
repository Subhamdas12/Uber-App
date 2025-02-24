package com.uberApp.Uber.App.strategies;

import org.springframework.stereotype.Component;

import com.uberApp.Uber.App.entities.enums.PaymentMethod;
import com.uberApp.Uber.App.strategies.impl.CashPaymentStrategy;
import com.uberApp.Uber.App.strategies.impl.WalletPaymentStrategy;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentStrategyManager {
    private final WalletPaymentStrategy walletPaymentStrategy;
    private final CashPaymentStrategy cashPaymentStrategy;

    public PaymentStrategy paymentStrategy(PaymentMethod paymentMethod) {
        return switch (paymentMethod) {
            case CASH -> cashPaymentStrategy;
            case WALLET -> walletPaymentStrategy;
        };
    }
}
