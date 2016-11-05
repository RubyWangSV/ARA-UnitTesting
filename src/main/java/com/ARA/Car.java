package com.ARA;

import java.util.List;
import java.util.UUID;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("car")
public class Car {

    @Id
    private String id;

    private String make;

    private String model;

    private String license;

    private String carType;

    private int maxPassengers;

    private String color;

    private List<String> validRideTypes;

    /**
     * keep an empty constructor so that morphia
     * can recreate this entity fetch it from
     * the database
     */
    public Car(){}


    /**
     * full constructor
     *
     * @param make
     * @param model
     * @param license
     * @param carType
     * @param maxPassengers
     * @param color
     * @param validRideTypes
     */
    public Car(String make, String model, String license,
                String carType, int maxPassengers, String color,
                List<String> validRideTypes) {
        super();
        this.id = UUID.randomUUID().toString();
        this.make = make;
        this.model = model;
        this.license = license;
        this.carType = carType;
        this.maxPassengers = maxPassengers;
        this.color = color;
        this.validRideTypes = validRideTypes;
    }




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public int getMaxPassengers() {
        return maxPassengers;
    }

    public void setMaxPassengers(int maxPassengers) {
        this.maxPassengers = maxPassengers;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<String> getValidRideTypes() {
        return validRideTypes;
    }

    public void setValidRideTypes(List<String> validRideTypes) {
        this.validRideTypes = validRideTypes;
    }
}
