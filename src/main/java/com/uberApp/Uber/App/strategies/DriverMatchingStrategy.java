package com.uberApp.Uber.App.strategies;

import com.uberApp.Uber.App.entities.DriverEntity;
import com.uberApp.Uber.App.entities.RideRequestEntity;
import java.util.List;

public interface DriverMatchingStrategy {
    List<DriverEntity> findMatchingDriver(RideRequestEntity rideRequest);
}
