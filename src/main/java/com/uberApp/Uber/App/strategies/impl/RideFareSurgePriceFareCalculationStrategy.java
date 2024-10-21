package com.uberApp.Uber.App.strategies.impl;

import org.springframework.stereotype.Service;

import com.uberApp.Uber.App.entities.RideRequestEntity;
import com.uberApp.Uber.App.services.DistanceService;
import com.uberApp.Uber.App.strategies.RideFareCalculationStrategy;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RideFareSurgePriceFareCalculationStrategy implements RideFareCalculationStrategy {

    private final DistanceService distanceService;
    private static final double SURGE_FACTOR = 2;

    @Override
    public double calculateFare(RideRequestEntity rideRequest) {
        double distance = distanceService.calculateDistance(rideRequest.getPickupLocation(),
                rideRequest.getDropOffLocation());
        return distance * RIDE_FARE_MULTIPLIER * SURGE_FACTOR;
    }

}
