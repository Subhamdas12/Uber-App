package com.uberApp.Uber.App.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.uberApp.Uber.App.dtos.DriverDTO;
import com.uberApp.Uber.App.dtos.RideDTO;
import com.uberApp.Uber.App.dtos.RiderDTO;
import com.uberApp.Uber.App.entities.DriverEntity;

public interface DriverService {

    RideDTO acceptRide(Long rideRequestId);

    RideDTO startRide(Long rideId, String otp);

    RideDTO cancelRide(Long rideId);

    RideDTO endRide(Long rideId);

    RiderDTO rateRider(Long rideId, Integer rating);

    Page<RideDTO> getAllMyRides(PageRequest pageRequest);

    DriverDTO getMyProfile();

    DriverEntity getCurrentDriver();

    DriverEntity updateDriverAvailability(DriverEntity driver, boolean isAvailable);

    DriverEntity createNewDriver(DriverEntity driver);

}
