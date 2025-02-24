package com.uberApp.Uber.App.services.impl;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uberApp.Uber.App.dtos.DriverDTO;
import com.uberApp.Uber.App.dtos.RideDTO;
import com.uberApp.Uber.App.dtos.RiderDTO;
import com.uberApp.Uber.App.entities.DriverEntity;
import com.uberApp.Uber.App.entities.RideEntity;
import com.uberApp.Uber.App.entities.RideRequestEntity;
import com.uberApp.Uber.App.entities.UserEntity;
import com.uberApp.Uber.App.entities.enums.RideRequestStatus;
import com.uberApp.Uber.App.entities.enums.RideStatus;
import com.uberApp.Uber.App.exceptions.ResourceNotFoundException;
import com.uberApp.Uber.App.repositories.DriverRepository;
import com.uberApp.Uber.App.services.DriverService;
import com.uberApp.Uber.App.services.PaymentService;
import com.uberApp.Uber.App.services.RatingService;
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
    private final PaymentService paymentService;
    private final RatingService ratingService;

    @Override
    @Transactional
    public RideDTO acceptRide(Long rideRequestId) {
        RideRequestEntity rideRequest = rideRequestService.findRideRequestById(rideRequestId);
        if (!rideRequest.getRideRequestStatus().equals(RideRequestStatus.PENDING)) {
            throw new RuntimeException(
                    "Ride Request cannot be accepted , status is " + rideRequest.getRideRequestStatus());
        }
        DriverEntity currentDriver = getCurrentDriver();
        if (!currentDriver.getIsAvailable()) {
            throw new RuntimeException("Driver cannot accept the ride because of unavailability ");
        }

        DriverEntity savedDriver = updateDriverAvailability(currentDriver, false);

        RideEntity ride = rideService.createNewRide(rideRequest, savedDriver);

        return modelMapper.map(ride, RideDTO.class);
    }

    @Override
    public RideDTO startRide(Long rideId, String otp) {
        RideEntity ride = rideService.getRideById(rideId);
        DriverEntity currentDriver = getCurrentDriver();
        if (!currentDriver.equals(ride.getDriver())) {
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
        paymentService.createNewPayment(savedRide);
        ratingService.createNewRating(savedRide);

        return modelMapper.map(savedRide, RideDTO.class);

    }

    @Override
    public DriverEntity getCurrentDriver() {
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return driverRepository.findByUser(user).orElseThrow(
                () -> new ResourceNotFoundException("Driver not associated with the user with id " + user.getId()));

    }

    @Override
    @Transactional
    public RideDTO endRide(Long rideId) {
        RideEntity ride = rideService.getRideById(rideId);
        DriverEntity currentDriver = getCurrentDriver();
        if (!currentDriver.equals(ride.getDriver())) {
            throw new RuntimeException("Driver cannot end a ride  as he has not started the ride");
        }
        if (!ride.getRideStatus().equals(RideStatus.ONGOING)) {
            throw new RuntimeException(
                    "Ride status is not ONGOING and hencec cannot be ended, status : " + ride.getRideStatus());
        }
        ride.setEndedAt(LocalDateTime.now());
        RideEntity savedRide = rideService.updateRideStatus(ride, RideStatus.ENDED);
        updateDriverAvailability(currentDriver, true);
        paymentService.processPayment(savedRide);
        return modelMapper.map(savedRide, RideDTO.class);

    }

    @Override
    public RideDTO cancelRide(Long rideId) {
        RideEntity ride = rideService.getRideById(rideId);
        DriverEntity currentDriver = getCurrentDriver();
        if (!currentDriver.equals(ride.getDriver())) {
            throw new RuntimeException("Driver cannot end a ride  as he has not started the ride");
        }

        if (!ride.getRideStatus().equals(RideStatus.CONFIRMED)) {
            throw new RuntimeException("Ride cannot be cancelled , invalid status " + ride.getRideStatus());
        }

        RideEntity savedRide = rideService.updateRideStatus(ride, RideStatus.CANCELLED);
        updateDriverAvailability(currentDriver, true);
        return modelMapper.map(savedRide, RideDTO.class);

    }

    @Override
    public DriverDTO getMyProfile() {
        DriverEntity currentDriver = getCurrentDriver();
        return modelMapper.map(currentDriver, DriverDTO.class);
    }

    @Override
    public DriverEntity updateDriverAvailability(DriverEntity driver, boolean isAvailable) {
        driver.setIsAvailable(isAvailable);
        return driverRepository.save(driver);
    }

    @Override
    public Page<RideDTO> getAllMyRides(PageRequest pageRequest) {
        DriverEntity driver = getCurrentDriver();
        return rideService.getAllRidesOfDriver(driver, pageRequest).map(ride -> modelMapper.map(ride, RideDTO.class));
    }

    @Override
    public DriverEntity createNewDriver(DriverEntity driver) {
        return driverRepository.save(driver);
    }

    @Override
    public RiderDTO rateRider(Long rideId, Integer rating) {
        RideEntity ride = rideService.getRideById(rideId);
        DriverEntity driver = getCurrentDriver();
        if (!driver.equals(ride.getDriver())) {
            throw new RuntimeException("Driver is not the owner of this ride");
        }

        if (!ride.getRideStatus().equals(RideStatus.ENDED)) {
            throw new RuntimeException(
                    "Ride status is not ended hence cannot start rating, status: " + ride.getRideStatus());
        }

        return ratingService.rateRider(ride, rating);

    }

}
