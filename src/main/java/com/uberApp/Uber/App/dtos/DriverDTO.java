package com.uberApp.Uber.App.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DriverDTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverDTO {
    private Long id;
    private UserDTO user;
    private Double rating;
    private Boolean available;
    private String vehicleId;
}