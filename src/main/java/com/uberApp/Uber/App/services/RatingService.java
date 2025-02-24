package com.uberApp.Uber.App.services;

import com.uberApp.Uber.App.dtos.DriverDTO;
import com.uberApp.Uber.App.dtos.RiderDTO;
import com.uberApp.Uber.App.entities.RideEntity;

public interface RatingService {
    DriverDTO rateDriver(RideEntity ride, Integer rating);

    RiderDTO rateRider(RideEntity ride, Integer rating);

    void createNewRating(RideEntity ride);

}
