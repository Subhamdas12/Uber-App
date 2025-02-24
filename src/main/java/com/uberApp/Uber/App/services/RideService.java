package com.uberApp.Uber.App.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.uberApp.Uber.App.entities.DriverEntity;
import com.uberApp.Uber.App.entities.RideEntity;
import com.uberApp.Uber.App.entities.RideRequestEntity;
import com.uberApp.Uber.App.entities.RiderEntity;
import com.uberApp.Uber.App.entities.enums.RideStatus;

public interface RideService {

    RideEntity createNewRide(RideRequestEntity rideRequest, DriverEntity driver);

    RideEntity getRideById(Long rideId);

    RideEntity updateRideStatus(RideEntity ride, RideStatus rideStatus);

    Page<RideEntity> getAllRidesOfRider(RiderEntity rider, PageRequest pageRequest);

    Page<RideEntity> getAllRidesOfDriver(DriverEntity driver, PageRequest pageRequest);

}
