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
        get("/", (req, res) -> "Hello World!");

        // session
        post(versionURI + "/session", (req, res) -> {
            try {

                JsonObject reqBody = (JsonObject) new JsonParser().parse(req.body());
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
                    res.status(400);
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

                res.status(200);
                return dataToJson.d2j(token);

            } catch (Exception e) {
                res.status(500);
                return dataToJson.d2j(new Error(500, 5000, e.getMessage()));
            }
        });

        // Access control
        // Only driver can create new cars
        before(versionURI + "/drivers/:id/cars", (req, res)
                -> {
            String jwt = req.queryParams("token");
            if (jwt == null) {
                halt(401, dataToJson.d2j(new Error(400, 9003, "No token provided")));
            }
            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(key)
                        .parseClaimsJws(jwt).getBody();
                long nowMillis = System.currentTimeMillis();
                Date now = new Date(nowMillis);
                if (claims.getExpiration().before(now)) {
                    halt(401, dataToJson.d2j(new Error(401, 9004, "Token expired")));
                }
                String id = claims.getSubject();
                String givenId = req.params(":id");
                if (!id.equals(givenId)) {
                    halt(401, dataToJson.d2j(new Error(401, 9002, "Failed to authenticate token")));
                }
            } catch (Exception e) {
                halt(401, dataToJson.d2j(new Error(401, 9002, "Failed to authenticate token")));
            }
        });

        // CRUD for Cars
        get(versionURI + "/cars", (req, res) -> carDAO.getAllCars(req, res));
        get(versionURI + "/cars/:id", (req, res) -> carDAO.getCar(req, res));
        patch(versionURI + "/cars/:id", (req, res) -> carDAO.updateCar(req, res));
        delete(versionURI + "/cars/:id", (req, res) -> carDAO.deleteCar(req, res));
        get(versionURI + "/cars/:id/drivers", (req, res) -> carDAO.getDriver(req, res));

        // CRUD for Drivers
        get(versionURI + "/drivers", (req, res) -> driverDAO.getAllDrivers(req, res));
        get(versionURI + "/drivers/:id", (req, res) -> driverDAO.getDriver(req, res));
        post(versionURI + "/drivers", (req, res) -> driverDAO.createDriver(req, res));
        patch(versionURI + "/drivers/:id", (req, res) -> driverDAO.updateDriver(req, res));
        delete(versionURI + "/drivers/:id", (req, res) -> driverDAO.deleteDriver(req, res));
        // Get and create car info of a driver
        get(versionURI + "/drivers/:id/cars", (req, res) -> driverDAO.getCars(req, res));
        post(versionURI + "/drivers/:id/cars", (req, res) -> driverDAO.createCar(req, res));
        // Get ride info of a driver
        get(versionURI + "/drivers/:id/rides", (req, res) -> driverDAO.getRides(req, res));

        // CRUD for Passengers
        get(versionURI + "/passengers", (req, res) -> passengerDAO.getAllPassengers(req, res));
        get(versionURI + "/passengers/:id", (req, res) -> passengerDAO.getPassenger(req, res));
        post(versionURI + "/passengers", (req, res) -> passengerDAO.createPassenger(req, res));
        patch(versionURI + "/passengers/:id", (req, res) -> passengerDAO.updatePassenger(req, res));
        delete(versionURI + "/passengers/:id", (req, res) -> passengerDAO.deletePassenger(req, res));
        // Get ride info of a driver
        get(versionURI + "/passengers/:id/rides", (req, res) -> passengerDAO.getRides(req, res));

        // CRUD for Rides
        get(versionURI + "/rides", (req, res) -> rideDAO.getAllRides(req, res));
        get(versionURI + "/rides/:id", (req, res) -> rideDAO.getRide(req, res));
        post(versionURI + "/rides", (req, res) -> rideDAO.createRide(req, res));
        patch(versionURI + "/rides/:id", (req, res) -> rideDAO.updateRide(req, res));
        delete(versionURI + "/rides/:id", (req, res) -> rideDAO.deleteRide(req, res));
        post(versionURI + "/rides/:id/routePoints", (req, res) -> rideDAO.addRoutePoint(req, res));
        get(versionURI + "/rides/:id/routePoints", (req, res) -> rideDAO.getRoutePoints(req, res));
        get(versionURI + "/rides/:id/routePoints/latest", (req, res) -> rideDAO.getLastestRoutePoints(req, res));
    }

}