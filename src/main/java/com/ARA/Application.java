package com.ARA;

import com.ARA.module.*;
import com.ARA.DAO.*;
import com.ARA.util.*;

import static spark.Spark.*;

import com.ARA.util.Error;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.util.Date;

public class Application {

    private static MorphiaService morphiaService;
    private static CarDAO carDAO;
    private static DriverDAO driverDAO;
    private static PassengerDAO passengerDAO;
    private static RideDAO rideDAO;
    private static SessionDAO sessionDAO;

    // sign JWT with key secret
    private static String key = "thunderbird";

    public Application() {
        // set up Morphia service
        this.morphiaService = new MorphiaService();
        this.carDAO = new CarDAO(Car.class, morphiaService.getDatastore());
        this.driverDAO = new DriverDAO(Driver.class, morphiaService.getDatastore());
        this.passengerDAO = new PassengerDAO(Passenger.class, morphiaService.getDatastore());
        this.rideDAO = new RideDAO(Ride.class, morphiaService.getDatastore());
        this.sessionDAO = new SessionDAO();
    }

    public static void main(String[] args) {
        // API version
        String versionURI = "/v1";

        Application app = new Application();
        // listen on port 8080
        port(8080);

        // server test
        get("/", (request, response) -> "Hello World!");

        // session
        post(versionURI + "/sessions", (request, response) -> sessionDAO.createToken(request, response));

        // Access control - token in header
        // Only driver can create new cars
        // Both driver and passenger can crete ride
        before(versionURI + "/drivers/:id/*", (request, response)
                -> {
            String tokenValue = request.headers("token");
            String path = request.pathInfo();
            String method = request.requestMethod();
            if (!(method == "POST" && (path.contains("cars") || path.contains("rides")))){
                halt(401, dataToJson.d2j(new Error(401, 1000, "Invalid resource.")));
            }
            if ( tokenValue == null) {
                halt(400, dataToJson.d2j(new Error(400, 9003, "No token provided")));
            }
            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(key)
                        .parseClaimsJws(tokenValue).getBody();
                long nowMillis = System.currentTimeMillis();
                Date now = new Date(nowMillis);
                if (claims.getExpiration().before(now)) {
                    halt(401, dataToJson.d2j(new Error(401, 9004, "Token expired")));
                }
                String id = claims.getSubject();
                String givenId = request.params(":id");
                if (!id.equals(givenId)) {
                    halt(401, dataToJson.d2j(new Error(401, 9002, "Failed to authenticate token")));
                }
            } catch (Exception e) {
                halt(401, dataToJson.d2j(new Error(401, 9002, "Failed to authenticate token")));
            }
        });


        // CRUD for Cars
        get(versionURI + "/cars", (request, response) -> carDAO.getAllCars(request, response));
        get(versionURI + "/cars/:id", (request, response) -> carDAO.getCar(request, response));
        patch(versionURI + "/cars/:id", (request, response) -> carDAO.updateCar(request, response));
        delete(versionURI + "/cars/:id", (request, response) -> carDAO.deleteCar(request, response));
        get(versionURI + "/cars/:id/drivers", (request, response) -> carDAO.getDriver(request, response));

        // CRUD for Drivers
        get(versionURI + "/drivers", (request, response) -> driverDAO.getAllDrivers(request, response));
        get(versionURI + "/drivers/:id", (request, response) -> driverDAO.getDriver(request, response));
        post(versionURI + "/drivers", (request, response) -> driverDAO.createDriver(request, response));
        patch(versionURI + "/drivers/:id", (request, response) -> driverDAO.updateDriver(request, response));
        delete(versionURI + "/drivers/:id", (request, response) -> driverDAO.deleteDriver(request, response));
        // Get and create car info of a driver
        get(versionURI + "/drivers/:id/cars", (request, response) -> driverDAO.getCars(request, response));
        post(versionURI + "/drivers/:id/cars", (request, response) -> driverDAO.createCar(request, response));

        // Get and create ride info of a driver
        get(versionURI + "/drivers/:id/rides", (request, response) -> driverDAO.getRides(request, response));
        post(versionURI + "/drivers/:id/rides", (request, response) -> driverDAO.createRide(request, response));

        // CRUD for Passengers
        get(versionURI + "/passengers", (request, response) -> passengerDAO.getAllPassengers(request, response));
        get(versionURI + "/passengers/:id", (request, response) -> passengerDAO.getPassenger(request, response));
        post(versionURI + "/passengers", (request, response) -> passengerDAO.createPassenger(request, response));
        patch(versionURI + "/passengers/:id", (request, response) -> passengerDAO.updatePassenger(request, response));
        delete(versionURI + "/passengers/:id", (request, response) -> passengerDAO.deletePassenger(request, response));
        // Get ride info of a driver
        get(versionURI + "/passengers/:id/rides", (request, response) -> passengerDAO.getRides(request, response));
        post(versionURI + "/passengers/:id/rides", (request, response) -> passengerDAO.createRide(request, response));

        // CRUD for Rides
        get(versionURI + "/rides", (request, response) -> rideDAO.getAllRides(request, response));
        get(versionURI + "/rides/:id", (request, response) -> rideDAO.getRide(request, response));
        patch(versionURI + "/rides/:id", (request, response) -> rideDAO.updateRide(request, response));
        delete(versionURI + "/rides/:id", (request, response) -> rideDAO.deleteRide(request, response));
        post(versionURI + "/rides/:id/routePoints", (request, response) -> rideDAO.addRoutePoint(request, response));
        get(versionURI + "/rides/:id/routePoints", (request, response) -> rideDAO.getRoutePoints(request, response));
        get(versionURI + "/rides/:id/routePoints/latest", (request, response) -> rideDAO.getLastestRoutePoints(request, response));

    }
}