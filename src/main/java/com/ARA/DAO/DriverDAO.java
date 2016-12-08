package com.ARA.DAO;

import com.ARA.Application;
import com.ARA.module.Car;
import com.ARA.module.Driver;

import com.ARA.module.Passenger;
import com.ARA.module.Ride;
import com.ARA.util.Error;
import com.ARA.util.dataToJson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import spark.Request;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import spark.Response;

import static spark.Spark.halt;


/**
 * implementation of Driver's Data Access Object
 * @author Edam & Ruby
 * @version 2.0.0
 */
public class DriverDAO extends BasicDAO<Driver, String> {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public DriverDAO(Class<Driver> entityClass, Datastore ds) {
        super(entityClass, ds);
    }

    /** This method is used to get all drivers.
     * @param request
     * @param response
     * @return all drivers
     * @throws IOException
     */
    public String getAllDrivers(Request request, Response response) throws IOException{
        try {

            Integer count = request.queryParams().contains("count") ? Integer.valueOf(request.queryParams("count")) : 2147483647;
            Integer offsetId = request.queryParams().contains("offsetId") ? Integer.valueOf(request.queryParams("offsetId")) : 0;
            String sortBy = request.queryParams().contains("sort") ? request.queryParams("sort") : "_id";

            if (request.queryParams().contains("sortOrder") && "DESC".equalsIgnoreCase(request.queryParams("sortOrder")))
                sortBy = "-" + sortBy;

            List<Driver> allDriver = getDs().find(Driver.class).offset(offsetId).limit(count).order(sortBy).asList();

            response.status(200);
            return dataToJson.d2j(allDriver);
        } catch (Exception e) {
            response.status(500);
            return dataToJson.d2j(new Error(500, 5000, e.getMessage()));
        }
    }

    /** This method is used to get a specific driver with :id.
     * @param request
     * @param response
     * @return The driver specified with :id.
     * @throws IOException
     */
    public String getDriver(Request request, Response response) throws IOException {
        try {
            String id = request.params(":id");
            Driver driver = getDs().find(Driver.class).field("id").equal(id).get();
            if (driver == null) {
                response.status(400);
                return dataToJson.d2j(new Error(400, 1003, "Given driver does not exist"));
            }
            response.status(200);
            return dataToJson.d2j(driver);
        } catch (Exception e) {
            response.status(500);
            return dataToJson.d2j(new Error(500, 5000, e.getMessage()));
        }
    }

    /** This method is used to create a driver.
     * @param request
     * @param response
     * @return The driver created.
     * @throws IOException
     */ 
    public String createDriver(Request request, Response response) throws IOException{
        try{
            JsonObject jsonObject = (JsonObject) new JsonParser().parse(request.body());

            String firstName = jsonObject.get("firstName").toString().replaceAll("\"", "");
            String lastName = jsonObject.get("lastName").toString().replaceAll("\"", "");
            String emailAddress = jsonObject.get("emailAddress").toString().replaceAll("\"", "");
            String password = jsonObject.get("password").toString().replaceAll("\"", "");
            String addressLine1 = jsonObject.get("addressLine1").toString().replaceAll("\"", "");
            String addressLine2 = jsonObject.get("addressLine2").toString().replaceAll("\"", "");
            String city = jsonObject.get("city").toString().replaceAll("\"", "");        
            String state = jsonObject.get("state").toString().replaceAll("\"", "");    
            String zip = jsonObject.get("zip").toString().replaceAll("\"", "");   
            String phoneNumber = jsonObject.get("phoneNumber").toString().replaceAll("\"", "");    
            String drivingLicense = jsonObject.get("drivingLicense").toString().replaceAll("\"", "");                    
            String licensedState = jsonObject.get("licensedState").toString().replaceAll("\"", "");
            if (password.length() > 20 || password.length() < 8) {
                response.status(400);
                return dataToJson.d2j(new Error(400, 2000, "Invalid data type"));
            }

            Driver newDriver = new Driver(firstName, lastName, emailAddress, password, addressLine1, addressLine2,city,state,zip,phoneNumber,drivingLicense,licensedState);

//            if (getDs().find(Driver.class).field("emailAddress").equal(emailAddress).get() != null
//                    || getDs().find(Passenger.class).field("emailAddress").equal(emailAddress).get() != null) {
//                response.status(400);
//                return dataToJson.d2j(new Error(400, 3000, "email address already taken"));
//            }

            if (!newDriver.isValidDriver()) {
                response.status(400);
                return dataToJson.d2j(new Error(400, 2000, "Invalid data type"));
            }

            getDs().save(newDriver);
            response.status(200);
            return dataToJson.d2j(newDriver);

        } catch (Exception e) {
            response.status(500);
            return dataToJson.d2j(new Error(500, 5000, e.getMessage()));
        }
    }

    /** This method is used to update a specific driver with :id.
     * @param request
     * @param response
     * @return The driver specified with :id.
     * @throws IOException
     */    
    public String updateDriver(Request request, Response response) throws IOException {
        try {
            String id = request.params(":id");
            JsonObject jsonObject = (JsonObject) new JsonParser().parse(request.body());

            Driver driver =  getDs().find(Driver.class).field("id").equal(id).get();

            if (jsonObject.has("firstName")) {
                String firstName = jsonObject.get("firstName").toString().replaceAll("\"", "");
                driver.setFirstName(firstName);
            }

            if (jsonObject.has("lastName")) {
                String lastName = jsonObject.get("lastName").toString().replaceAll("\"", "");
                driver.setLastName(lastName);
            }

            if (jsonObject.has("emailAddress")) {
                String emailAddress = jsonObject.get("emailAddress").toString().replaceAll("\"", "");
                if (getDs().find(Driver.class).field("emailAddress").equal(emailAddress).get() != null
                        || getDs().find(Passenger.class).field("emailAddress").equal(emailAddress).get() != null) {
                    response.status(400);
                    return dataToJson.d2j(new Error(400, 3000, "email address already taken"));
                }
                driver.setEmailAddress(emailAddress);
            }

            if (jsonObject.has("password")) {
                String password = jsonObject.get("password").toString().replaceAll("\"", "");
                if (password.length() > 20 || password.length() < 8) {
                    response.status(400);
                    return dataToJson.d2j(new Error(400, 2000, "Invalid data type"));
                }
                driver.setPassword(password);
            }

            if (jsonObject.has("addressLine1")) {
                String addressLine1 = jsonObject.get("addressLine1").toString().replaceAll("\"", "");
                driver.setAddressLine1(addressLine1);
            }

            if (jsonObject.has("addressLine2")) {
                String addressLine2 = jsonObject.get("addressLine2").toString().replaceAll("\"", "");
                driver.setAddressLine2(addressLine2);
            }

            if (jsonObject.has("city")) {
                String city = jsonObject.get("city").toString().replaceAll("\"", "");
                driver.setCity(city);
            }        

            if (jsonObject.has("state")) {
                String state = jsonObject.get("state").toString().replaceAll("\"", "");
                driver.setState(state);
            }

            if (jsonObject.has("zip")) {
                String zip= jsonObject.get("zip").toString().replaceAll("\"", "");
                driver.setZip(zip);
            }

            if (jsonObject.has("phoneNumber")) {
                String phoneNumber = jsonObject.get("phoneNumber").toString().replaceAll("\"", "");
                driver.setPhoneNumber(phoneNumber);
            }

            if (jsonObject.has("drivingLicense")) {
                String drivingLicense = jsonObject.get("drivingLicense").toString().replaceAll("\"", "");
                driver.setDrivingLicense(drivingLicense);
            }

            if (jsonObject.has("licensedState")) {
                String licensedState = jsonObject.get("licensedState").toString().replaceAll("\"", "");
                driver.setLicensedState(licensedState);
            }

            if (!driver.isValidDriver()) {
                response.status(400);
                return dataToJson.d2j(new Error(400, 2000, "Invalid data type"));
            }

            getDs().save(driver);
            response.status(200);
            return dataToJson.d2j(driver);
        } catch (Exception e) {
            response.status(500);
            return dataToJson.d2j(new Error(500, 5000, e.getMessage()));
        }

    }

    /** This method is used to delete a specific driver with :id.
     * @param request
     * @param response
     * @return The driver with :id.
     * @throws IOException
     */ 
    public String deleteDriver(Request request, Response response) throws IOException {
        try {
            String id = request.params(":id");
            Driver driver = getDs().find(Driver.class).field("id").equal(id).get();
            if (driver == null) {
                response.status(400);
                return dataToJson.d2j(new Error(400, 1003, "Given driver does not exist"));
            }
            getDs().delete(driver);
            response.status(200);
            return dataToJson.d2j(driver);
        } catch (Exception e) {
            response.status(500);
            return dataToJson.d2j(new Error(500, 5000, e.getMessage()));
        }
    }

    /** This method is used to get the car info of a specific driver with :id.
     * @param request
     * @param response
     * @return The car info of a specific driver with :id.
     * @throws IOException
     */ 
    public String getCars(Request request, Response response) throws IOException {
        try {
            String id = request.params(":id");
            Driver driver = getDs().find(Driver.class).field("id").equal(id).get();
            if (driver == null) {
                response.status(400);
                return dataToJson.d2j(new Error(400, 1003, "Given driver does not exist"));
            }
            List<String> carIds = driver.getCars();
            List<Car> cars = new ArrayList<>();
            if (carIds.size() > 0) {
                for (String carId : carIds) {
                    Car car = getDs().find(Car.class).field("id").equal(carId).get();
                    if (car == null) {
                        response.status(400);
                        return dataToJson.d2j(new Error(400, 1002, "Given car does not exist"));
                    }
                    cars.add(car);
                }
            }
            response.status(200);
            return dataToJson.d2j(cars);
        } catch (Exception e) {
            response.status(500);
            return dataToJson.d2j(new Error(500, 5000, e.getMessage()));
        }
    }

    /** This method is used to create cars of a specific driver with :id.
     * @param request
     * @param response
     * @return The newe car created for a specific driver with :id.
     * @throws IOException
     */ 
    public String createCar(Request request, Response response) throws IOException {
        // Access control
        // Only driver can create new cars


        String key = "thunderbird";

        String jwt = request.queryParams("token");
        if (jwt == null) {
            return dataToJson.d2j(new Error(401, 9003, "No token provided"));
        }
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(jwt).getBody();
            long nowMillis = System.currentTimeMillis();
            Date now = new Date(nowMillis);
            if (claims.getExpiration().before(now)) {
                return dataToJson.d2j(new Error(401, 9004, "Token expired"));
            }
            String id = claims.getSubject();
            String givenId = request.params(":id");
            if (!id.equals(givenId)) {
                return dataToJson.d2j(new Error(401, 9002, "Failed to authenticate token"));
            }
        } catch (Exception e) {
            return dataToJson.d2j(new Error(401, 9002, "Failed to authenticate token"));
        }

        try {
            String id = request.params(":id");
            Driver driver = getDs().find(Driver.class).field("id").equal(id).get();
            if (driver == null) {
                response.status(400);
                return dataToJson.d2j(new Error(400, 1003, "Given driver does not exist"));
            }
            // Create a new car
            JsonObject jsonObject = (JsonObject) new JsonParser().parse(request.body());
            String make = jsonObject.get("make").toString().replaceAll("\"", "");
            String model = jsonObject.get("model").toString().replaceAll("\"", "");
            String license = jsonObject.get("license").toString().replaceAll("\"", "");
            String carType = jsonObject.get("carType").toString().replaceAll("\"", "");
            Integer maxPassengers = Integer.valueOf(jsonObject.get("maxPassengers").toString());
            String color = jsonObject.get("color").toString().replaceAll("\"", "");
            Type listType = new TypeToken<ArrayList<String>>() {
            }.getType();
            List<String> validRideTypes = new Gson().fromJson(jsonObject.get("validRideTypes").getAsJsonArray(), listType);

            Car newCar = new Car(make, model, license, carType, maxPassengers, color, validRideTypes);
            newCar.setDrivers(id);

            if (!newCar.isValidCar()) {
                response.status(400);
                return dataToJson.d2j(new Error(400, 2000, "Invalid data type"));
            }
            // save to Cars
            getDs().save(newCar);
            // associate new car to current driver
            driver.addCar(newCar.getId());
            getDs().save(driver);
            response.status(200);
            return dataToJson.d2j(newCar);
        } catch (Exception e) {
            response.status(500);
            return dataToJson.d2j(new Error(500, 5000, e.getMessage()));
        }
    }

    /** This method is used to get the ride info of a specific driver with :id.
     * @param request
     * @param response
     * @return The ride info of a specific driver with :id.
     * @throws IOException
     */ 
    public String getRides(Request request, Response response) throws IOException {
        try {
            String id = request.params(":id");
            Driver driver = getDs().find(Driver.class).field("id").equal(id).get();
            if (driver == null) {
                response.status(400);
                return dataToJson.d2j(new Error(400, 1003, "Given driver does not exist"));
            }
            List<String> rideIds = driver.getRides();
            List<Ride> rides = new ArrayList<>();
            if (rideIds.size() > 0) {
                for (String rideId : rideIds) {
                    Ride ride = getDs().find(Ride.class).field("id").equal(rideId).get();
                    if (ride == null) {
                        response.status(400);
                        return dataToJson.d2j(new Error(400, 1005, "Given ride does not exist"));
                    }
                    rides.add(ride);
                }
            }
            response.status(200);
            return dataToJson.d2j(rides);
        } catch (Exception e) {
            response.status(500);
            return dataToJson.d2j(new Error(500, 5000, e.getMessage()));
        }
    }

    /** This method is used to create a ride and attach it to current driver.
     * @param request
     * @param response
     * @return The ride created.
     * @throws IOException
     */
    public String createRide(Request request, Response response) throws IOException{
        try{
            String id = request.params(":id");
            Driver driver = getDs().find(Driver.class).field("id").equal(id).get();
            if (driver == null) {
                response.status(400);
                return dataToJson.d2j(new Error(400, 1003, "Given driver does not exist"));
            }

            JsonObject jsonObject = (JsonObject) new JsonParser().parse(request.body());

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

            String passengerId = jsonObject.get("passenger").toString().replaceAll("\"", "");

            Passenger passenger = getDs().find(Passenger.class).field("id").equal(passengerId).get();

            if (!newRide.isValidRide() || passenger == null) {
                response.status(400);
                return dataToJson.d2j(new Error(400, 2000, "Invalid data type"));
            }

            newRide.setDriver(id);
            newRide.setPassenger(passengerId);

            getDs().save(newRide);

            // associate new ride to current driver and passenger
            driver.addRide(newRide.getId());
            getDs().save(driver);
            passenger.addRide(newRide.getId());
            getDs().save(passenger);
            response.status(200);
            return dataToJson.d2j(newRide);

        } catch (Exception e) {
            response.status(500);
            return dataToJson.d2j(new Error(500, 5000, e.getMessage()));
        }
    }
}