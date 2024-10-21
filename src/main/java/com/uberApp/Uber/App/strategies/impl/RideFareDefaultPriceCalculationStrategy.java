package com.uberApp.Uber.App.strategies.impl;

import org.springframework.stereotype.Service;

import com.uberApp.Uber.App.entities.RideRequestEntity;
import com.uberApp.Uber.App.services.DistanceService;
import com.uberApp.Uber.App.strategies.RideFareCalculationStrategy;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RideFareDefaultPriceCalculationStrategy implements RideFareCalculationStrategy {
    private final DistanceService distanceService;

    @Override
    public double calculateFare(RideRequestEntity rideRequest) {
        double distance = distanceService.calculateDistance(rideRequest.getPickupLocation(),
                rideRequest.getDropOffLocation());
        return distance * RIDE_FARE_MULTIPLIER;
    }

}
