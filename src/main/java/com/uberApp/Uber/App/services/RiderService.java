package com.uberApp.Uber.App.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

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

    Page<RideDTO> getAllMyRides(PageRequest pageRequest);

    RiderEntity createNewRider(UserEntity user);

    RiderEntity getCurrentRider();

}
