package com.uberApp.Uber.App.strategies;

import com.uberApp.Uber.App.entities.RideRequestEntity;

public interface RideFareCalculationStrategy {
    double RIDE_FARE_MULTIPLIER = 10;

    double calculateFare(RideRequestEntity rideRequest);
}
