package com.ARA.util;

import com.ARA.module.Car;

import java.util.List;
import java.util.Arrays;

/**
 * Validate data type
 */
public class Validate {
    public static boolean validCar (Car car) {
        if (car.getMake().length() > 50 || car.getModel().length() > 50 || car.getCarType().length() > 10
                || car.getColor().length() > 10 || car.getColor().length() > 20)
            return false;
        List<String> rideTypes = car.getValidRideTypes();
        for (String type : rideTypes) {
            if (!rideTypes.contains(type))
                return false;
        }
        return true;
    }
}
