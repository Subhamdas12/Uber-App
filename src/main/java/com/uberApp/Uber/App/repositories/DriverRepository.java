package com.uberApp.Uber.App.repositories;

import java.util.List;

import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.uberApp.Uber.App.entities.DriverEntity;

@Repository
public interface DriverRepository extends JpaRepository<DriverEntity, Long> {

        @Query(value = "SELECT d.*, ST_Distance(d.current_location,:pickupLocation) AS distance "
                        + "FROM driver d WHERE d.is_available=true AND ST_DWithin(d.current_location,:pickupLocation,10000) ORDER BY distance LIMIT 10", nativeQuery = true)
        List<DriverEntity> findTenNearestDrivers(Point pickupLocation);

        @Query(value = "SELECT d.* "
                        + "FROM driver d WHERE d.is_available=true AND ST_DWithin(d.current_location,:pickupLocation,15000) ORDER BY d.rating DESC LIMIT 10", nativeQuery = true)
        List<DriverEntity> findTenNearbyTopRatedDriver(Point pickupLocation);

}
