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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.time.LocalDateTime;


/**
 * implementation of Ride's Data Access Object
 * @author Edam
 * @version 2.0.0
 */
public class RideDAO extends BasicDAO<Ride, String> {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public RideDAO(Class<Ride> entityClass, Datastore ds) {
        super(entityClass, ds);
    }

    /** This method is used to get all rides.
     * @param request
     * @param response
     * @return all rides
     * @throws IOException
     */
    public String getAllRides(Request request, Response response) throws IOException {
        try {

            Integer count = request.queryParams().contains("count") ? Integer.valueOf(request.queryParams("count")) : 2147483647;
            Integer offsetId = request.queryParams().contains("offsetId") ? Integer.valueOf(request.queryParams("offsetId")) : 0;
            String sortBy = request.queryParams().contains("sort") ? request.queryParams("sort") : "_id";

            if (request.queryParams().contains("sortOrder") && "DESC".equalsIgnoreCase(request.queryParams("sortOrder")))
                sortBy = "-" + sortBy;

            List<Ride> allRide = getDs().find(Ride.class).offset(offsetId).limit(count).order(sortBy).asList();

            response.status(200);
            return dataToJson.d2j(allRide);
        } catch (Exception e) {
            response.status(500);
            return dataToJson.d2j(new Error(500, 5000, e.getMessage()));
        }
    }

    /** This method is used to get a specific ride with :id.
     * @param request
     * @param response
     * @return The ride specified with :id.
     * @throws IOException
     */
    public String getRide(Request request, Response response) throws IOException {
        try {
            String id = request.params(":id");
            Ride ride = getDs().find(Ride.class).field("id").equal(id).get();
            if (ride == null) {
                response.status(400);
                return dataToJson.d2j(new Error(400, 1005, "Given ride does not exist"));
            }
            response.status(200);
            return dataToJson.d2j(ride);
        } catch (Exception e) {
            response.status(500);
            return dataToJson.d2j(new Error(500, 5000, e.getMessage()));
        }
    }

    /** This method is used to update a specific ride with :id.
     * @param request
     * @param response
     * @return The ride specified with :id.
     * @throws IOException
     */    
    public String updateRide(Request request, Response response) throws IOException {
        try{
            String id = request.params(":id");
            JsonObject jsonObject = (JsonObject) new JsonParser().parse(request.body());

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
            response.status(200);
            return dataToJson.d2j(ride);

        } catch (Exception e) {
            response.status(500);
            return dataToJson.d2j(new Error(500, 5000, e.getMessage()));
        }
    }

    /** This method is used to delete a specific ride with :id.
     * @param request
     * @param response
     * @return The ride specified with :id.
     * @throws IOException
     */
    public String deleteRide(Request request, Response response) throws IOException {
        try {
            String id = request.params(":id");
            Ride ride = getDs().find(Ride.class).field("id").equal(id).get();
            if (ride == null) {
                response.status(400);
                return dataToJson.d2j(new Error(400, 1005, "Given ride does not exist"));
            }
            getDs().delete(ride);
            response.status(200);
            return dataToJson.d2j(ride);
        } catch (Exception e) {
            response.status(500);
            return dataToJson.d2j(new Error(500, 5000, e.getMessage()));
        }
    }

    /** This method is used to add a route point of a ride.
     * @param request
     * @param response
     * @return The route point created.
     * @throws IOException
     */
    public String addRoutePoint(Request request, Response response) throws IOException {
        try {
            String id = request.params(":id");
            Ride ride = getDs().find(Ride.class).field("id").equal(id).get();
            if (ride == null) {
                response.status(400);
                return dataToJson.d2j(new Error(400, 1005, "Given ride does not exist"));
            }
            JsonObject jsonObject = (JsonObject) new JsonParser().parse(request.body());
            if (jsonObject.has("timestamp") && jsonObject.has("latitude") && jsonObject.has("longitude")) {
                Long timestamp = Long.valueOf(jsonObject.get("timestamp").toString());
                Double latitude = Double.valueOf(jsonObject.get("latitude").toString());
                Double longitude = Double.valueOf(jsonObject.get("longitude").toString());
                routePoint newRoutePoint = new routePoint(timestamp, latitude, longitude);
                getDs().save(newRoutePoint);
                ride.addRoutePoints(newRoutePoint);
                getDs().save(ride);
                response.status(200);
                return dataToJson.d2j(newRoutePoint);
            } else {
                response.status(400);
                return dataToJson.d2j(new Error(400, 2000, "Invalid data type"));
            }
        } catch (Exception e) {
            response.status(500);
            return dataToJson.d2j(new Error(500, 5000, e.getMessage()));
        }
    }

    /** This method is used to get all route points of a specific ride with :id.
     * @param request
     * @param response
     * @return The route points of a specific ride with :id.
     * @throws IOException
     */
    public String getRoutePoints(Request request, Response response) throws IOException {
        try {
            String id = request.params(":id");
            Ride ride = getDs().find(Ride.class).field("id").equal(id).get();
            if (ride == null) {
                response.status(400);
                return dataToJson.d2j(new Error(400, 1005, "Given ride does not exist"));
            }
            List<routePoint> routePoints = ride.getRoutePoints();
            Collections.sort(routePoints, Comparator.comparing(routePoint::getTimestamp));
            response.status(200);
            return dataToJson.d2j(routePoints);
        } catch (Exception e) {
            response.status(500);
            return dataToJson.d2j(new Error(500, 5000, e.getMessage()));
        }
    }

    /** This method is used to get the lastest route point of a specific ride with :id.
     * @param request
     * @param response
     * @return The lastest route point of a specific ride with :id.
     * @throws IOException
     */
    public String getLastestRoutePoints(Request request, Response response) throws IOException {
        try {
            String id = request.params(":id");
            Ride ride = getDs().find(Ride.class).field("id").equal(id).get();
            if (ride == null) {
                response.status(400);
                return dataToJson.d2j(new Error(400, 1005, "Given ride does not exist"));
            }
            List<routePoint> routePoints = ride.getRoutePoints();
            Collections.sort(routePoints, Comparator.comparing(routePoint::getTimestamp));
            response.status(200);
            return dataToJson.d2j(routePoints.get(routePoints.size() - 1));
        } catch (Exception e) {
            response.status(500);
            return dataToJson.d2j(new Error(500, 5000, e.getMessage()));
        }
    }
}
