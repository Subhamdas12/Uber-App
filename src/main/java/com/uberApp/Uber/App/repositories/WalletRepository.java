package com.uberApp.Uber.App.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uberApp.Uber.App.entities.UserEntity;
import com.uberApp.Uber.App.entities.WalletEntity;

public interface WalletRepository extends JpaRepository<WalletEntity, Long> {

    Optional<WalletEntity> findByUser(UserEntity user);

}
