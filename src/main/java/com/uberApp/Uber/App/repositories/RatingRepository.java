package com.uberApp.Uber.App.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uberApp.Uber.App.entities.DriverEntity;
import com.uberApp.Uber.App.entities.RatingEntity;
import com.uberApp.Uber.App.entities.RideEntity;
import com.uberApp.Uber.App.entities.RiderEntity;

public interface RatingRepository extends JpaRepository<RatingEntity, Long> {

    Optional<RatingEntity> findByRide(RideEntity ride);

    List<RatingEntity> findByDriver(DriverEntity driver);

    List<RatingEntity> findByRider(RiderEntity rider);

}
