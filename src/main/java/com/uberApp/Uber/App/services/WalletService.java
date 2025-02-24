package com.uberApp.Uber.App.services;

import com.uberApp.Uber.App.entities.RideEntity;
import com.uberApp.Uber.App.entities.UserEntity;
import com.uberApp.Uber.App.entities.WalletEntity;
import com.uberApp.Uber.App.entities.enums.TransactionMethod;

public interface WalletService {
    WalletEntity addMoneyToWallet(UserEntity user, Double amount, String transactionId, RideEntity ride,
            TransactionMethod transactionMethod);

    WalletEntity deductMoneyFromWallet(UserEntity user, Double amount, String transactionId, RideEntity ride,
            TransactionMethod transactionMethod);

    void withdrawAllMyMoneyFromWallet();

    WalletEntity findWalletById(Long walletId);

    WalletEntity createNewWallet(UserEntity user);

    WalletEntity findByUser(UserEntity user);

}
