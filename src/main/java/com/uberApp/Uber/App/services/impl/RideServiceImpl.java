package com.uberApp.Uber.App.services.impl;

import java.util.Random;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.uberApp.Uber.App.entities.DriverEntity;
import com.uberApp.Uber.App.entities.RideEntity;
import com.uberApp.Uber.App.entities.RideRequestEntity;
import com.uberApp.Uber.App.entities.RiderEntity;
import com.uberApp.Uber.App.entities.enums.RideRequestStatus;
import com.uberApp.Uber.App.entities.enums.RideStatus;
import com.uberApp.Uber.App.exceptions.ResourceNotFoundException;
import com.uberApp.Uber.App.repositories.RideRepository;
import com.uberApp.Uber.App.services.RideRequestService;
import com.uberApp.Uber.App.services.RideService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {
    private final ModelMapper modelMapper;
    private final RideRequestService rideRequestService;
    private final RideRepository rideRepository;

    @Override
    public RideEntity createNewRide(RideRequestEntity rideRequest, DriverEntity driver) {
        rideRequest.setRideRequestStatus(RideRequestStatus.CONFIRMED);
        RideEntity ride = modelMapper.map(rideRequest, RideEntity.class);
        ride.setRideStatus(RideStatus.CONFIRMED);
        ride.setDriver(driver);
        ride.setOtp(generateRandomOTP());
        ride.setId(null);
        rideRequestService.update(rideRequest);
        return rideRepository.save(ride);
    }

    private String generateRandomOTP() {
        Random random = new Random();
        int otpInt = random.nextInt(10000); // 0 to 9999
        return String.format("%04d", otpInt);
    }

    @Override
    public RideEntity getRideById(Long rideId) {
        return rideRepository.findById(rideId)
                .orElseThrow(() -> new ResourceNotFoundException("Ride not found with id " + rideId));
    }

    @Override
    public RideEntity updateRideStatus(RideEntity ride, RideStatus rideStatus) {
        ride.setRideStatus(rideStatus);
        return rideRepository.save(ride);
    }

    @Override
    public Page<RideEntity> getAllRidesOfRider(RiderEntity rider, PageRequest pageRequest) {
        return rideRepository.findByRider(rider, pageRequest);
    }

    @Override
    public Page<RideEntity> getAllRidesOfDriver(DriverEntity driver, PageRequest pageRequest) {
        return rideRepository.findByDriver(driver, pageRequest);
    }

}
