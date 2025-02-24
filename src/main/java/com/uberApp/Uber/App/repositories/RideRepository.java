package com.uberApp.Uber.App.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.uberApp.Uber.App.entities.DriverEntity;
import com.uberApp.Uber.App.entities.RideEntity;
import com.uberApp.Uber.App.entities.RiderEntity;

public interface RideRepository extends JpaRepository<RideEntity, Long> {
    Page<RideEntity> findByDriver(DriverEntity driver, Pageable pageRequest);

    Page<RideEntity> findByRider(RiderEntity rider, Pageable pageRequest);
}
