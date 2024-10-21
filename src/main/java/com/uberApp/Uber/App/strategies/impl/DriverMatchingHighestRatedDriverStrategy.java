package com.uberApp.Uber.App.strategies.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.uberApp.Uber.App.entities.DriverEntity;
import com.uberApp.Uber.App.entities.RideRequestEntity;
import com.uberApp.Uber.App.repositories.DriverRepository;
import com.uberApp.Uber.App.strategies.DriverMatchingStrategy;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DriverMatchingHighestRatedDriverStrategy implements DriverMatchingStrategy {

    private final DriverRepository driverRepository;

    @Override
    public List<DriverEntity> findMatchingDriver(RideRequestEntity rideRequest) {
        return driverRepository.findTenNearbyTopRatedDriver(rideRequest.getPickupLocation());
    }

}
