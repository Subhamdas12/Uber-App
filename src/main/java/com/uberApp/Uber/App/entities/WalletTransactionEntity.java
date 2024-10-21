package com.uberApp.Uber.App.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.uberApp.Uber.App.entities.enums.TransactionMethod;
import com.uberApp.Uber.App.entities.enums.TransactionType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "wallet_transaction")
public class WalletTransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    private TransactionType transactionType;
    private TransactionMethod transactionMethod;

    @OneToOne
    private RideEntity rideEntity;

    private String transactionId;

    @ManyToOne
    private WalletEntity wallet;

    @CreationTimestamp
    private LocalDateTime timeStamp;

}