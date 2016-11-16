package com.ARA.DAO;

import com.ARA.module.Car;
import com.ARA.module.Ride;

import com.ARA.module.routePoint;
import com.ARA.util.Error;
import com.ARA.util.dataToJson;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

import spark.Request;
import spark.Response;

import java.io.IOException;
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

    public String getAllRides(Request req, Response res) throws IOException {
        try {

            Integer count = req.queryParams().contains("count") ? Integer.valueOf(req.queryParams("count")) : 2147483647;
            Integer offsetId = req.queryParams().contains("offsetId") ? Integer.valueOf(req.queryParams("offsetId")) : 0;
            String sortBy = req.queryParams().contains("sort") ? req.queryParams("sort") : "_id";

            if (req.queryParams().contains("sortOrder") && "DESC".equalsIgnoreCase(req.queryParams("sortOrder")))
                sortBy = "-" + sortBy;

            List<Ride> allRide = getDs().find(Ride.class).offset(offsetId).limit(count).order(sortBy).asList();

            res.status(200);
            return dataToJson.d2j(allRide);
        } catch (Exception e) {
            res.status(500);
            return dataToJson.d2j(new Error(500, 5000, e.getMessage()));
        }
    }

    public String getRide(Request req, Response res) throws IOException {
        try {
            String id = req.params(":id");
            Ride ride = getDs().find(Ride.class).field("id").equal(id).get();
            if (ride == null) {
                res.status(400);
                return dataToJson.d2j(new Error(400, 1005, "Given ride does not exist"));
            }
            res.status(200);
            return dataToJson.d2j(ride);
        } catch (Exception e) {
            res.status(500);
            return dataToJson.d2j(new Error(500, 5000, e.getMessage()));
        }
    }

    public String createRide(Request req, Response res) throws IOException{
        try{
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

            if (!newRide.isValidRide()) {
                res.status(400);
                return dataToJson.d2j(new Error(400, 2000, "Invalid data type"));
            }

            getDs().save(newRide);
            res.status(200);
            return dataToJson.d2j(newRide);

        } catch (Exception e) {
            res.status(500);
            return dataToJson.d2j(new Error(500, 5000, e.getMessage()));
        }
    }

    public String updateRide(Request req, Response res) throws IOException {
        try{
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
            res.status(200);
            return dataToJson.d2j(ride);

        } catch (Exception e) {
            res.status(500);
            return dataToJson.d2j(new Error(500, 5000, e.getMessage()));
        }
    }

    public String deleteRide(Request req, Response res) throws IOException {
        try {
            String id = req.params(":id");
            Ride ride = getDs().find(Ride.class).field("id").equal(id).get();
            if (ride == null) {
                res.status(400);
                return dataToJson.d2j(new Error(400, 1005, "Given ride does not exist"));
            }
            getDs().delete(ride);
            res.status(200);
            return dataToJson.d2j(ride);
        } catch (Exception e) {
            res.status(500);
            return dataToJson.d2j(new Error(500, 5000, e.getMessage()));
        }
    }

    public String addRoutePoint(Request req, Response res) throws IOException {
        try {
            String id = req.params(":id");
            Ride ride = getDs().find(Ride.class).field("id").equal(id).get();
            if (ride == null) {
                res.status(400);
                return dataToJson.d2j(new Error(400, 1005, "Given ride does not exist"));
            }
            JsonObject jsonObject = (JsonObject) new JsonParser().parse(req.body());
            if (jsonObject.has("timestamp") && jsonObject.has("latitude") && jsonObject.has("longitude")) {
                String timestamp = jsonObject.get("rideType").toString().replaceAll("\"", "");
                Double latitude = Double.valueOf(jsonObject.get("latitude").toString());
                Double longitude = Double.valueOf(jsonObject.get("longitude").toString());
                routePoint newRoutePoint = new routePoint(timestamp, latitude, longitude);
                getDs().save(newRoutePoint);
                ride.addRoutePoints(newRoutePoint);
                getDs().save(ride);
                res.status(200);
                return dataToJson.d2j(newRoutePoint);
            } else {
                res.status(400);
                return dataToJson.d2j(new Error(400, 2000, "Invalid data type"));
            }
        } catch (Exception e) {
            res.status(500);
            return dataToJson.d2j(new Error(500, 5000, e.getMessage()));
        }
    }
}
