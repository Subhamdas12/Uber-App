package com.uberApp.Uber.App.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uberApp.Uber.App.entities.RideRequestEntity;

@Repository
public interface RideRequestRepository extends JpaRepository<RideRequestEntity, Long> {

}
