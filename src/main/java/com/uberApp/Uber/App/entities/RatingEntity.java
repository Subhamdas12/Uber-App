package com.uberApp.Uber.App.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rating", indexes = {
        @Index(name = "idx_rating_ride", columnList = "ride_id"),
        @Index(name = "idx_rating_driver", columnList = "driver_id"),
        @Index(name = "idx_rating_rider", columnList = "rider_id")
})
@Getter
@Setter
@Builder
public class RatingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private RideEntity ride;

    @ManyToOne
    private RiderEntity rider;

    @ManyToOne
    private DriverEntity driver;

    private Integer riderRating;
    private Integer driverRating;

}
