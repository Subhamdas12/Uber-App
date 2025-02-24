package com.uberApp.Uber.App.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uberApp.Uber.App.entities.PaymentEntity;
import com.uberApp.Uber.App.entities.RideEntity;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    Optional<PaymentEntity> findByRide(RideEntity ride);

}
