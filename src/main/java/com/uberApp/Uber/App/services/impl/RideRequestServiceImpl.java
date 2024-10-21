package com.uberApp.Uber.App.services.impl;

import org.springframework.stereotype.Service;

import com.uberApp.Uber.App.entities.RideRequestEntity;
import com.uberApp.Uber.App.exceptions.ResourceNotFoundException;
import com.uberApp.Uber.App.repositories.RideRequestRepository;
import com.uberApp.Uber.App.services.RideRequestService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RideRequestServiceImpl implements RideRequestService {

    private final RideRequestRepository rideRequestRepository;

    @Override
    public RideRequestEntity findRideRequestById(Long rideRequestId) {
        return rideRequestRepository.findById(rideRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("RideRequest not found with id " + rideRequestId));
    }

    @Override
    public void update(RideRequestEntity rideRequest) {
        rideRequestRepository.findById(rideRequest.getId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("RideRequest not found with id " + rideRequest.getId()));
        rideRequestRepository.save(rideRequest);
    }
}
