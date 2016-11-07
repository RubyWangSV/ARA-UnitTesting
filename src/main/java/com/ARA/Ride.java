package com.ARA;

import java.util.List;
import java.util.UUID;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("ride")
public class Ride {

    @Id
    private String id;

    private String rideType;

    private List<Double> startPoint;

    private List<Double> endPoint;

    private String requestTime;

    private String pickupTime;

    private String dropOffTime;

    private String status; // [REQUESTED, AWAITING_DRIVER, DRIVE_ASSIGNED, IN_PROGRESS, ARRIVED, CLOSED]

    private Double fare;

    /**
     * keep an empty constructor so that morphia
     * can recreate this entity fetch it from
     * the database
     */
    public Ride(){}


    /**
     * full constructor
     *
     * @param rideType
     * @param startPoint
     * @param endPoint
     * @param requestTime
     * @param pickupTime
     * @param dropOffTime
     * @param status
     * @param fare
     */
    public Ride(String rideType, List<Double> startPoint, List<Double> endPoint,
                String requestTime, String pickupTime, String dropOffTime, String status,
                Double fare) {
        super();
        this.id = UUID.randomUUID().toString();
        this.rideType = rideType;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.requestTime = requestTime;
        this.pickupTime = pickupTime;
        this.dropOffTime = dropOffTime;
        this.status = status;
        this.fare = fare;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRideType() { return rideType; }

    public void setRideType(String rideType) { this.rideType = rideType; }

    public List<Double> getStartPoint() { return startPoint; }

    public void setStartPoint(List<Double> startPoint) { this.startPoint = startPoint; }

    public List<Double> getEndPoint() { return endPoint; }

    public void setEndPoint(List<Double> endPoint) { this.endPoint = endPoint; }

    public String getRequestTime() { return requestTime; }

    public void setRequestTime(String requestTime) { this.requestTime = requestTime; }

    public String getPickupTime() { return pickupTime; }

    public void setPickupTime(String  pickupTime) { this.pickupTime = pickupTime; }

    public String getDropOffTime() { return dropOffTime; }

    public void setDropOffTime(String dropOffTime) { this.dropOffTime = dropOffTime; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public Double getFare() { return fare; }

    public void setFare(Double fare) { this.fare = fare; }
}
