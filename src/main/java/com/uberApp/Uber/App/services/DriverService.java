package com.uberApp.Uber.App.services;

import com.uberApp.Uber.App.dtos.RideDTO;
import com.uberApp.Uber.App.entities.DriverEntity;

public interface DriverService {

    RideDTO acceptRide(Long rideRequestId);

    RideDTO startRide(Long rideId, String otp);

    DriverEntity getCurentDriver();

}
