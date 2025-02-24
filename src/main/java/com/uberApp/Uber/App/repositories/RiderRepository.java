package com.uberApp.Uber.App.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uberApp.Uber.App.entities.RiderEntity;
import com.uberApp.Uber.App.entities.UserEntity;

@Repository
public interface RiderRepository extends JpaRepository<RiderEntity, Long> {

    Optional<RiderEntity> findByUser(UserEntity user);

}
