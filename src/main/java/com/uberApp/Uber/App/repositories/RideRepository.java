package com.uberApp.Uber.App.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uberApp.Uber.App.entities.RideEntity;

public interface RideRepository extends JpaRepository<RideEntity, Long> {

}
