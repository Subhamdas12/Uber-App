package com.uberApp.Uber.App.utils;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

import com.uberApp.Uber.App.dtos.PointDTO;

public class GeometryUtils {

    public static Point createPoint(PointDTO pointDTO) {
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        Coordinate coordinate = new Coordinate(pointDTO.getCoordinates()[0], pointDTO.getCoordinates()[1]);
        return geometryFactory.createPoint(coordinate);
    }

}
