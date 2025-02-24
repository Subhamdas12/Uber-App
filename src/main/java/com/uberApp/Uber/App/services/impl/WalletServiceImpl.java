package com.uberApp.Uber.App.services.impl;

import org.springframework.stereotype.Service;

import com.uberApp.Uber.App.entities.RideEntity;
import com.uberApp.Uber.App.entities.UserEntity;
import com.uberApp.Uber.App.entities.WalletEntity;
import com.uberApp.Uber.App.entities.enums.TransactionMethod;
import com.uberApp.Uber.App.exceptions.ResourceNotFoundException;
import com.uberApp.Uber.App.repositories.WalletRepository;
import com.uberApp.Uber.App.services.WalletService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;

    @Override
    public WalletEntity addMoneyToWallet(UserEntity user, Double amount, String transactionId, RideEntity ride,
            TransactionMethod transactionMethod) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addMoneyToWallet'");
    }

    @Override
    public WalletEntity deductMoneyFromWallet(UserEntity user, Double amount, String transactionId, RideEntity ride,
            TransactionMethod transactionMethod) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deductMoneyFromWallet'");
    }

    @Override
    public void withdrawAllMyMoneyFromWallet() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'withdrawAllMyMoneyFromWallet'");
    }

    @Override
    public WalletEntity findWalletById(Long walletId) {
        return walletRepository.findById(walletId)
                .orElseThrow(() -> new ResourceNotFoundException("No wallet found with id " + walletId));
    }

    @Override
    public WalletEntity createNewWallet(UserEntity user) {
        WalletEntity wallet = new WalletEntity();
        wallet.setUser(user);
        return walletRepository.save(wallet);

    }

    @Override
    public WalletEntity findByUser(UserEntity user) {
        return walletRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found for user with id " + user.getId()));
    }

}
