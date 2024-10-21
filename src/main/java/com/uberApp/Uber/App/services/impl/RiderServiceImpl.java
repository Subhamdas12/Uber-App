package com.uberApp.Uber.App.services.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uberApp.Uber.App.dtos.DriverDTO;
import com.uberApp.Uber.App.dtos.RideDTO;
import com.uberApp.Uber.App.dtos.RideRequestDTO;
import com.uberApp.Uber.App.dtos.RiderDTO;
import com.uberApp.Uber.App.entities.DriverEntity;
import com.uberApp.Uber.App.entities.RideRequestEntity;
import com.uberApp.Uber.App.entities.RiderEntity;
import com.uberApp.Uber.App.entities.UserEntity;
import com.uberApp.Uber.App.entities.enums.RideRequestStatus;
import com.uberApp.Uber.App.exceptions.ResourceNotFoundException;
import com.uberApp.Uber.App.repositories.RideRequestRepository;
import com.uberApp.Uber.App.repositories.RiderRepository;
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

        // TODO:Send notification to all the drivers about this ride request

        return modelMapper.map(savedRideRequest, RideRequestDTO.class);

    }

    @Override
    public RideDTO cancelRide(Long rideId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cancelRide'");
    }

    @Override
    public DriverDTO rateDriver(Long rideId, Integer rating) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'rateDriver'");
    }

    @Override
    public RiderDTO getMyProfile() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMyProfile'");
    }

    @Override
    public List<RideDTO> getAllMyRides() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllMyRides'");
    }

    @Override
    public RiderEntity getCurrentRider() {
        return riderRepository.findById(1L)
                .orElseThrow(() -> new ResourceNotFoundException("Rider not found with id : " + 1));
    }

}
