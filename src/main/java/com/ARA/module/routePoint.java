package com.ARA.module;

import java.util.UUID;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
@Entity("routepoint")
public class routePoint {
    @Id
    private String id;
    private String timestamp;
    private Double latitude;
    private Double longitude;

    public routePoint() {}

    /**
     * full constructor
     *
     * @param timestamp
     * @param latitude
     * @param longitude
     */
    public routePoint(String timestamp, Double latitude, Double longitude) {
        super();
        this.id = UUID.randomUUID().toString();
        this.timestamp = timestamp;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
