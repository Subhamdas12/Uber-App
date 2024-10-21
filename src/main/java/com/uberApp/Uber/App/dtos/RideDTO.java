package com.uberApp.Uber.App.dtos;

import java.time.LocalDateTime;

import com.uberApp.Uber.App.entities.enums.PaymentMethod;
import com.uberApp.Uber.App.entities.enums.RideStatus;

import lombok.Data;

/**
 * RideDTO
 */
@Data
public class RideDTO {
    private Long id;
    private PointDTO pickUpLocation;
    private PointDTO dropOffLocation;

    private LocalDateTime createdTime;
    private RiderDTO rider;
    private DriverDTO driver;
    private PaymentMethod paymentMethod;
    private RideStatus rideStatus;

    private String otp;
    private Double fare;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;

}