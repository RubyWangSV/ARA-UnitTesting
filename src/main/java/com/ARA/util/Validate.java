package com.ARA.util;

import com.ARA.module.Car;
import com.ARA.module.Driver;

import java.util.List;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Validate data type
 */
public class Validate {
    public static boolean validCar (Car car) {
        if (car.getMake().length() > 50 || car.getModel().length() > 50 || car.getCarType().length() > 10
                || car.getColor().length() > 10 || car.getColor().length() > 20)
            return false;
        List<String> rideTypes = car.getValidRideTypes();
        List<String> validRideTypes = Arrays.asList("ECONOMY", "PREMIUM", "EXECUTIVE");
        for (String type : rideTypes) {
            if (!validRideTypes.contains(type))
                return false;
        }
        return true;
    }
    public static boolean validDriver (Driver driver) {
        HashMap<String, Integer> driverProperty = new HashMap<String, Integer>();
        driverProperty.put("firstName", 50);
        driverProperty.put("lastName", 50);
        driverProperty.put("emailAddress", 50);
        driverProperty.put("password", 50);
        driverProperty.put("addressLine1", 50);
        driverProperty.put("addressLine2", 50);
        driverProperty.put("city", 50);
        driverProperty.put("state", 50);
        driverProperty.put("zip", 50);
        driverProperty.put("city", 50);
        driverProperty.put("phoneNumber", 50);
        driverProperty.put("drivingLicense", 50);
        driverProperty.put("drivingLicense", 2);

        if (driver.getFirstName().length() > 50 || driver.getLastName().length() > 50 || driver.getEmailAddress().length() > 50
                || driver.getAddressLine1().length() > 100
                || driver.getCity().length() > 50 || driver.getState().length() != 2 || driver.getZip().length() != 5 
                || driver.getDrivingLicense().length() > 16 || driver.getLicensedState().length() > 2)
            return false;
        return true;
    }
}