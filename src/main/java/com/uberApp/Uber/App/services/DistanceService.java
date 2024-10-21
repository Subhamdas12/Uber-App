package com.uberApp.Uber.App.services;

import org.locationtech.jts.geom.Point;

public interface DistanceService {

    double calculateDistance(Point pickupLocation, Point dropOffLocation);

}
