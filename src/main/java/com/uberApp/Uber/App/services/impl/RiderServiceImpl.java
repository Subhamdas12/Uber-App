package com.uberApp.Uber.App.services.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uberApp.Uber.App.dtos.DriverDTO;
import com.uberApp.Uber.App.dtos.RideDTO;
import com.uberApp.Uber.App.dtos.RideRequestDTO;
import com.uberApp.Uber.App.dtos.RiderDTO;
import com.uberApp.Uber.App.entities.DriverEntity;
import com.uberApp.Uber.App.entities.RideEntity;
import com.uberApp.Uber.App.entities.RideRequestEntity;
import com.uberApp.Uber.App.entities.RiderEntity;
import com.uberApp.Uber.App.entities.UserEntity;
import com.uberApp.Uber.App.entities.enums.RideRequestStatus;
import com.uberApp.Uber.App.entities.enums.RideStatus;
import com.uberApp.Uber.App.exceptions.ResourceNotFoundException;
import com.uberApp.Uber.App.repositories.RideRequestRepository;
import com.uberApp.Uber.App.repositories.RiderRepository;
import com.uberApp.Uber.App.services.DriverService;
import com.uberApp.Uber.App.services.RatingService;
import com.uberApp.Uber.App.services.RideService;
import com.uberApp.Uber.App.services.RiderService;
import com.uberApp.Uber.App.strategies.RideStrategyManager;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RiderServiceImpl implements RiderService {
    private final RiderRepository riderRepository;
    private final ModelMapper modelMapper;
    private final RideStrategyManager rideStrategyManager;
    private final RideRequestRepository rideRequestRepository;
    private final RideService rideService;
    private final RatingService ratingService;
    private final DriverService driverService;

    @Override
    public RiderEntity createNewRider(UserEntity user) {
        RiderEntity riderEntity = RiderEntity.builder().user(user).rating(0.0).build();
        return riderRepository.save(riderEntity);
    }

    @Override
    @Transactional
    public RideRequestDTO requestRide(RideRequestDTO rideRequestDTO) {
        RiderEntity rider = getCurrentRider();
        RideRequestEntity rideRequest = modelMapper.map(rideRequestDTO, RideRequestEntity.class);
        rideRequest.setRider(rider);
        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);

        Double fare = rideStrategyManager.rideFareCalculationStrategy().calculateFare(rideRequest);
        rideRequest.setFare(fare);

        RideRequestEntity savedRideRequest = rideRequestRepository.save(rideRequest);

        List<DriverEntity> drivers = rideStrategyManager.driverMatchingStrategy(rider.getRating())
                .findMatchingDriver(rideRequest);

        return modelMapper.map(savedRideRequest, RideRequestDTO.class);

    }

    @Override
    public RideDTO cancelRide(Long rideId) {
        RiderEntity rider = getCurrentRider();
        RideEntity ride = rideService.getRideById(rideId);
        if (!rider.equals(ride.getRider())) {
            throw new RuntimeException("Rider doesnot own the ride with id : " + rideId);
        }
        if (!ride.getRideStatus().equals(RideStatus.CONFIRMED)) {
            throw new RuntimeException("Ride cannot be cancelled , invalid status : " + ride.getRideStatus());
        }

        RideEntity savedRide = rideService.updateRideStatus(ride, RideStatus.CANCELLED);
        driverService.updateDriverAvailability(ride.getDriver(), false);
        return modelMapper.map(savedRide, RideDTO.class);
    }

    @Override
    public DriverDTO rateDriver(Long rideId, Integer rating) {
        RideEntity ride = rideService.getRideById(rideId);
        RiderEntity rider = getCurrentRider();
        if (!rider.equals(ride.getRider())) {
            throw new RuntimeException("Rider is not the owner of this ride");
        }

        if (!ride.getRideStatus().equals(RideStatus.ENDED)) {
            throw new RuntimeException(
                    "Ride status is not ended hence cannot start rating, status: " + ride.getRideStatus());
        }

        return ratingService.rateDriver(ride, rating);
    }

    @Override
    public RiderDTO getMyProfile() {
        RiderEntity currentRider = getCurrentRider();
        return modelMapper.map(currentRider, RiderDTO.class);
    }

    @Override
    public RiderEntity getCurrentRider() {
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return riderRepository.findByUser(user).orElseThrow(
                () -> new ResourceNotFoundException("Rider not associated with user with id " + user.getId()));

    }

    @Override
    public Page<RideDTO> getAllMyRides(PageRequest pageRequest) {
        RiderEntity rider = getCurrentRider();
        return rideService.getAllRidesOfRider(rider, pageRequest).map(ride -> modelMapper.map(ride, RideDTO.class));
    }

}
