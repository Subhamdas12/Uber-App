package com.uberApp.Uber.App.dtos;

import java.time.LocalDateTime;

import com.uberApp.Uber.App.entities.enums.PaymentMethod;
import com.uberApp.Uber.App.entities.enums.RideRequestStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RideRequestDTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideRequestDTO {
    private Long id;
    private PointDTO pickupLocation;
    private PointDTO dropOffLocation;
    private PaymentMethod paymentMethod;
    private LocalDateTime localDateTime;
    private RiderDTO rider;
    private Double fare;
    private RideRequestStatus rideRequestStatus;
}