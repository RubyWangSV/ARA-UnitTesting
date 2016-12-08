package com.ARA;

import com.ARA.module.*;
import com.ARA.DAO.*;
import com.ARA.util.*;

import static spark.Spark.*;
import static spark.route.HttpMethod.before;

import com.ARA.util.Error;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import org.mongodb.morphia.query.Query;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

import javax.xml.bind.DatatypeConverter;


public class Application {

    private static MorphiaService morphiaService;
    private static CarDAO carDAO;
    private static DriverDAO driverDAO;
    private static PassengerDAO passengerDAO;
    private static RideDAO rideDAO;

    // sign JWT with key secret
    private static String key = "thunderbird";

    public Application() {
        // set up Morphia service
        this.morphiaService = new MorphiaService();
        this.carDAO = new CarDAO(Car.class, morphiaService.getDatastore());
        this.driverDAO = new DriverDAO(Driver.class, morphiaService.getDatastore());
        this.passengerDAO = new PassengerDAO(Passenger.class, morphiaService.getDatastore());
        this.rideDAO = new RideDAO(Ride.class, morphiaService.getDatastore());

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
        post(versionURI + "/sessions", (request, response) -> {
            try {

                JsonObject reqBody = (JsonObject) new JsonParser().parse(request.body());
                String email = reqBody.get("email").toString().replaceAll("\"", "");
                String password = reqBody.get("password").toString().replaceAll("\"", "");
                Driver driver = driverDAO.createQuery()
                        .filter("emailAddress", email)
                        .get();

                Passenger passenger = passengerDAO.createQuery()
                        .filter("emailAddress", email)
                        .get();

                String driverPassword = driver != null ? driver.getPassword() : null;
                String passengerPassword = passenger != null ? passenger.getPassword() : null;

                PasswordEncoder pe = new PasswordEncoder();

                boolean isDriver = pe.validatePassword(password, driverPassword);
                boolean isPassenger = pe.validatePassword(password, passengerPassword);

                if (!isDriver && !isPassenger) {
                    response.status(400);
                    return dataToJson.d2j(new Error(400, 9001, "Wrong username or password"));
                }

                String userId = (driver != null) ? driver.getId() : passenger.getId();

                // the JWT signature algorithm will be using to sign the token
                SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

                long nowMillis = System.currentTimeMillis();
                Date now = new Date(nowMillis);

                // set the JWT Claims, userId as body
                JwtBuilder builder = Jwts.builder()
                        .setIssuedAt(now)
                        .setSubject(userId)
                        .signWith(signatureAlgorithm, key);

                // token will expired in 10 min
                long ttlMillis = 10 * 60 * 1000;
                long expMillis = nowMillis + ttlMillis;
                Date exp = new Date(expMillis);
                builder.setExpiration(exp);

                String tokenString = builder.compact();

                token token = new token(tokenString);

                response.status(200);
                return dataToJson.d2j(token);

            } catch (Exception e) {
                response.status(500);
                return dataToJson.d2j(new Error(500, 5000, e.getMessage()));
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

        // Get ride info of a driver
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