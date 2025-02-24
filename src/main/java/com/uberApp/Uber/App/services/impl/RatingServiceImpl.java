package com.uberApp.Uber.App.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.uberApp.Uber.App.dtos.DriverDTO;
import com.uberApp.Uber.App.dtos.RiderDTO;
import com.uberApp.Uber.App.entities.DriverEntity;
import com.uberApp.Uber.App.entities.RatingEntity;
import com.uberApp.Uber.App.entities.RideEntity;
import com.uberApp.Uber.App.entities.RiderEntity;
import com.uberApp.Uber.App.exceptions.ResourceNotFoundException;
import com.uberApp.Uber.App.exceptions.RuntimeConflictException;
import com.uberApp.Uber.App.repositories.DriverRepository;
import com.uberApp.Uber.App.repositories.RatingRepository;
import com.uberApp.Uber.App.repositories.RiderRepository;
import com.uberApp.Uber.App.services.RatingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final DriverRepository driverRepository;
    private final RiderRepository riderRepository;
    private final ModelMapper modelMapper;

    @Override
    public DriverDTO rateDriver(RideEntity ride, Integer rating) {
        DriverEntity driver = ride.getDriver();
        RatingEntity ratingObj = ratingRepository.findByRide(ride).orElseThrow(
                () -> new ResourceNotFoundException("Rating not found for the ride with id : " + ride.getId()));
        if (ratingObj.getDriverRating() != null) {
            throw new RuntimeConflictException("Driver has already been rated , cannot rate again");
        }
        ratingObj.setDriverRating(rating);
        ratingRepository.save(ratingObj);

        Double newRating = ratingRepository.findByDriver(driver).stream()
                .mapToDouble(rating1 -> rating1.getDriverRating()).average().orElse(0.0);

        driver.setRating(newRating);
        DriverEntity savedDriver = driverRepository.save(driver);
        return modelMapper.map(savedDriver, DriverDTO.class);
    }

    @Override
    public RiderDTO rateRider(RideEntity ride, Integer rating) {
        RiderEntity rider = ride.getRider();
        RatingEntity ratingObj = ratingRepository.findByRide(ride).orElseThrow(
                () -> new ResourceNotFoundException("Rating not found for the ride with id " + ride.getId()));

        if (ratingObj.getRiderRating() != null) {
            throw new RuntimeConflictException("Rider has already been rated, cannot rate again");
        }

        ratingObj.setRiderRating(rating);
        ratingRepository.save(ratingObj);

        Double newRating = ratingRepository.findByRider(rider).stream().mapToDouble(rating1 -> rating1.getRiderRating())
                .average().orElse(0.0);

        rider.setRating(newRating);
        RiderEntity savedRider = riderRepository.save(rider);
        return modelMapper.map(savedRider, RiderDTO.class);
    }

    @Override
    public void createNewRating(RideEntity ride) {
        RatingEntity rating = RatingEntity.builder().driver(ride.getDriver()).rider(ride.getRider()).ride(ride).build();
        ratingRepository.save(rating);
    }

}
