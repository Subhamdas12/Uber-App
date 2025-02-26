package com.uberApp.Uber.App.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RiderDTO
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RiderDTO {
    private UserDTO user;
    private Double rating;
}