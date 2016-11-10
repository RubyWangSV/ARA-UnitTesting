package com.ARA.util;

import com.ARA.module.Car;
import com.ARA.module.Driver;

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
    public static boolean validDriver (Driver driver) {
        if (driver.getFirstName().length() > 50 || driver.getLastName().length() > 50 || driver.getEmailAddress().length() > 50
                || driver.getPassword().length() > 20 || driver.getPassword().length() < 8 || driver.getAddressLine1().length() > 100 
                || driver.getCity().length() > 50 || driver.getState().length() != 2 || driver.getZip().length() != 5 
                || driver.getDrivingLicense().length() > 16 || driver.getLicensedState().length() > 2)
            return false;
        return true;
    }
}