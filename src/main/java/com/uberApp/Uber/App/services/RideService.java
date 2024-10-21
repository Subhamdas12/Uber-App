package com.uberApp.Uber.App.services;

import com.uberApp.Uber.App.entities.DriverEntity;
import com.uberApp.Uber.App.entities.RideEntity;
import com.uberApp.Uber.App.entities.RideRequestEntity;
import com.uberApp.Uber.App.entities.enums.RideStatus;

public interface RideService {

    RideEntity createNewRide(RideRequestEntity rideRequest, DriverEntity driver);

    RideEntity getRideById(Long rideId);

    RideEntity updateRideStatus(RideEntity ride, RideStatus rideStatus);

}
