package com.ARA.module;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * implementation of Car
 * @author Edam
 * @version 2.0.0
 */

@Entity("routepoint")
@JsonIgnoreProperties({"validRoutePoint"})
public class routePoint {
    @Id
    private String id;
    private Long timestamp;
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
    public routePoint(Long timestamp, Double latitude, Double longitude) {
        super();
        this.id = UUID.randomUUID().toString();
        this.timestamp = timestamp;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setTimestamp(Long timestamp) { this.timestamp = timestamp; }

    public Long getTimestamp() { return timestamp; }

    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLatitude() { return latitude; }

    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public Double getLongitude() { return longitude; }
}
