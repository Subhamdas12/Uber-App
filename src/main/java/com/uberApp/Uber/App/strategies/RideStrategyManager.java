package com.uberApp.Uber.App.strategies;

import org.springframework.stereotype.Component;

import com.uberApp.Uber.App.strategies.impl.DriverMatchingHighestRatedDriverStrategy;
import com.uberApp.Uber.App.strategies.impl.DriverMatchingNearestDriverStrategy;
import com.uberApp.Uber.App.strategies.impl.RideFareDefaultPriceCalculationStrategy;
import com.uberApp.Uber.App.strategies.impl.RideFareSurgePriceFareCalculationStrategy;

import lombok.RequiredArgsConstructor;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class RideStrategyManager {
    private final DriverMatchingNearestDriverStrategy nearestDriverStrategy;
    private final DriverMatchingHighestRatedDriverStrategy highestRatedDriverStrategy;
    private final RideFareDefaultPriceCalculationStrategy defaultPriceCalculationStrategy;
    private final RideFareSurgePriceFareCalculationStrategy surgePriceFareCalculationStrategy;

    public DriverMatchingStrategy driverMatchingStrategy(double riderRating) {
        if (riderRating >= 4.8) {
            return highestRatedDriverStrategy;
        } else {
            return nearestDriverStrategy;
        }
    }

    public RideFareCalculationStrategy rideFareCalculationStrategy() {
        LocalTime surgeStartTime = LocalTime.of(18, 0);
        LocalTime surgeEndTime = LocalTime.of(21, 0);
        LocalTime currentTime = LocalTime.now();

        boolean isSurgeTime = currentTime.isAfter(surgeStartTime) && currentTime.isBefore(surgeEndTime);
        if (isSurgeTime) {
            return surgePriceFareCalculationStrategy;
        } else {
            return defaultPriceCalculationStrategy;
        }

    }

}
