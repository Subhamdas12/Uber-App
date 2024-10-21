package com.uberApp.Uber.App.services.impl;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.uberApp.Uber.App.dtos.RideDTO;
import com.uberApp.Uber.App.entities.DriverEntity;
import com.uberApp.Uber.App.entities.RideEntity;
import com.uberApp.Uber.App.entities.RideRequestEntity;
import com.uberApp.Uber.App.entities.enums.RideRequestStatus;
import com.uberApp.Uber.App.entities.enums.RideStatus;
import com.uberApp.Uber.App.exceptions.ResourceNotFoundException;
import com.uberApp.Uber.App.repositories.DriverRepository;
import com.uberApp.Uber.App.services.DriverService;
import com.uberApp.Uber.App.services.RideRequestService;
import com.uberApp.Uber.App.services.RideService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {
    private final DriverRepository driverRepository;
    private final RideRequestService rideRequestService;
    private final RideService rideService;
    private final ModelMapper modelMapper;

    @Override
    public RideDTO acceptRide(Long rideRequestId) {
        RideRequestEntity rideRequest = rideRequestService.findRideRequestById(rideRequestId);
        if (!rideRequest.getRideRequestStatus().equals(RideRequestStatus.PENDING)) {
            throw new RuntimeException(
                    "Ride Request cannot be accepted , status is " + rideRequest.getRideRequestStatus());
        }
        DriverEntity currentDriver = getCurentDriver();
        if (!currentDriver.getIsAvailable()) {
            throw new RuntimeException("Driver cannot accept the ride because of unavailability ");
        }

        currentDriver.setIsAvailable(false);
        DriverEntity savedDriver = driverRepository.save(currentDriver);

        RideEntity ride = rideService.createNewRide(rideRequest, savedDriver);

        return modelMapper.map(ride, RideDTO.class);
    }

    @Override
    public RideDTO startRide(Long rideId, String otp) {
        RideEntity ride = rideService.getRideById(rideId);
        DriverEntity driver = getCurentDriver();
        if (!driver.equals(ride.getDriver())) {
            throw new RuntimeException("Driver cannot start a ride as he has not accepted it earlier");
        }
        if (!ride.getRideStatus().equals(RideStatus.CONFIRMED)) {
            throw new RuntimeException(
                    "Ride status is not CONFIRMED hence cannot be started , status: " + ride.getRideStatus());
        }
        if (!otp.equals(ride.getOtp())) {
            throw new RuntimeException("OTP not valid, otp : " + otp);
        }
        ride.setStartedAt(LocalDateTime.now());
        RideEntity savedRide = rideService.updateRideStatus(ride, RideStatus.ONGOING);
        return modelMapper.map(savedRide, RideDTO.class);

    }

    @Override
    public DriverEntity getCurentDriver() {
        return driverRepository.findById(2L)
                .orElseThrow(() -> new ResourceNotFoundException("  Driver not found with id " + 2));
    }
}
