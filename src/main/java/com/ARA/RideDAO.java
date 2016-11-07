package com.ARA;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import spark.Request;

import java.lang.reflect.Type;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;


/**
 * implementation of Ride's Data Access Object
 * @author Edam
 *
 */
public class RideDAO extends BasicDAO<Ride, String> {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public RideDAO(Class<Ride> entityClass, Datastore ds) {
        super(entityClass, ds);
    }

    public List<Ride> getAllRides() {
        return getDs().find(Ride.class).asList();
    }

    public Ride getRide(Request req) {
        String id = req.params(":id");
        return getDs().find(Ride.class).field("id").equal(id).get();
    }

    public Ride createRide(Request req) {

        JsonObject jsonObject = (JsonObject) new JsonParser().parse(req.body());

        String rideType = jsonObject.get("rideType").toString().replaceAll("\"", "");

        Type listType = new TypeToken<ArrayList<Double>>(){}.getType();
        List<Double> startPoint = new Gson().fromJson(jsonObject.get("startPoint").getAsJsonArray(), listType);

        List<Double> endPoint = new Gson().fromJson(jsonObject.get("endPoint").getAsJsonArray(), listType);

        LocalDateTime requestTime = LocalDateTime.parse(jsonObject.get("requestTime").toString().replaceAll("\"", ""), formatter);

        LocalDateTime pickupTime = LocalDateTime.parse(jsonObject.get("pickupTime").toString().replaceAll("\"", ""), formatter);

        LocalDateTime dropOffTime = LocalDateTime.parse(jsonObject.get("dropOffTime").toString().replaceAll("\"", ""), formatter);

        String status = jsonObject.get("status").toString().replaceAll("\"", "");

        Double fare = Double.parseDouble(jsonObject.get("fare").toString().replaceAll("\"", ""));

        Ride newRide = new Ride(rideType, startPoint, endPoint, requestTime.format(formatter), pickupTime.format(formatter), dropOffTime.format(formatter), status, fare);
        getDs().save(newRide);

        return newRide;
    }

    public Ride updateRide(Request req) {
        String id = req.params(":id");
        JsonObject jsonObject = (JsonObject) new JsonParser().parse(req.body());

        Ride ride =  getDs().find(Ride.class).field("id").equal(id).get();

        if (jsonObject.has("rideType")) {
            String rideType = jsonObject.get("rideType").toString().replaceAll("\"", "");

            ride.setRideType(rideType);
        }

        if (jsonObject.has("startPoint")) {
            Type listType = new TypeToken<ArrayList<Double>>(){}.getType();
            List<Double> startPoint = new Gson().fromJson(jsonObject.get("startPoint").getAsJsonArray(), listType);

            ride.setStartPoint(startPoint);
        }

        if (jsonObject.has("endPoint")) {
            Type listType = new TypeToken<ArrayList<Double>>(){}.getType();
            List<Double> endPoint = new Gson().fromJson(jsonObject.get("endPoint").getAsJsonArray(), listType);

            ride.setEndPoint(endPoint);
        }

        if (jsonObject.has("requestTime")) {
            LocalDateTime requestTime = LocalDateTime.parse(jsonObject.get("requestTime").toString().replaceAll("\"", ""), formatter);

            ride.setRequestTime(requestTime.format(formatter));
        }

        if (jsonObject.has("pickupTime")) {
            LocalDateTime pickupTime = LocalDateTime.parse(jsonObject.get("pickupTime").toString().replaceAll("\"", ""), formatter);

            ride.setPickupTime(pickupTime.format(formatter));
        }

        if (jsonObject.has("dropOffTime")) {
            LocalDateTime dropOffTime = LocalDateTime.parse(jsonObject.get("dropOffTime").toString().replaceAll("\"", ""), formatter);

            ride.setDropOffTime(dropOffTime.format(formatter));
        }

        if (jsonObject.has("status")) {
            String status = jsonObject.get("status").toString().replaceAll("\"", "");

            ride.setStatus(status);
        }

        if (jsonObject.has("fare")) {
            Double fare = Double.parseDouble(jsonObject.get("fare").toString().replaceAll("\"", ""));

            ride.setFare(fare);
        }

        getDs().save(ride);
        return ride;
    }

    public Ride deleteRide(Request req) {
        String id = req.params(":id");
        Ride ride =  getDs().find(Ride.class).field("id").equal(id).get();
        getDs().delete(ride);
        return ride;
    }
}
