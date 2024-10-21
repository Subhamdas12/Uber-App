package com.uberApp.Uber.App.services;

import java.util.List;

import com.uberApp.Uber.App.dtos.DriverDTO;
import com.uberApp.Uber.App.dtos.RideDTO;
import com.uberApp.Uber.App.dtos.RideRequestDTO;
import com.uberApp.Uber.App.dtos.RiderDTO;
import com.uberApp.Uber.App.entities.RiderEntity;
import com.uberApp.Uber.App.entities.UserEntity;

public interface RiderService {

    RideRequestDTO requestRide(RideRequestDTO rideRequestDto);

    RideDTO cancelRide(Long rideId);

    DriverDTO rateDriver(Long rideId, Integer rating);

    RiderDTO getMyProfile();

    List<RideDTO> getAllMyRides();

    RiderEntity createNewRider(UserEntity user);

    RiderEntity getCurrentRider();

}
