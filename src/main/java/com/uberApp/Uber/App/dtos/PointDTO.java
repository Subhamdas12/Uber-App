package com.uberApp.Uber.App.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * PointDTO
 */
@Data
@NoArgsConstructor
public class PointDTO {
    private double[] coordinates;
    private String type = "Point";

    public PointDTO(double[] coordinates) {
        this.coordinates = coordinates;
    }
}