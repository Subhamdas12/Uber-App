package com.uberApp.Uber.App.services;

import com.uberApp.Uber.App.entities.RideRequestEntity;

public interface RideRequestService {

    RideRequestEntity findRideRequestById(Long rideRequestId);

    void update(RideRequestEntity rideRequest);
}
