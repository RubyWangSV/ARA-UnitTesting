package com.ARA.DAO;

import com.ARA.module.Passenger;

import com.ARA.module.Ride;
import com.ARA.util.Error;
import com.ARA.util.dataToJson;

import spark.Request;

import java.io.IOException;
import java.util.*;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import spark.Response;


/**
 * implementation of Passenger's Data Access Object
 * @author Edam & Ruby
 *
 */
public class PassengerDAO extends BasicDAO<Passenger, String> {

    public PassengerDAO(Class<Passenger> entityClass, Datastore ds) {
        super(entityClass, ds);
    }

    public String getAllPassengers(Request req, Response res) throws IOException{
         try {

             Integer count = req.queryParams().contains("count") ? Integer.valueOf(req.queryParams("count")) : 2147483647;
             Integer offsetId = req.queryParams().contains("offsetId") ? Integer.valueOf(req.queryParams("offsetId")) : 0;
             String sortBy = req.queryParams().contains("sort") ? req.queryParams("sort") : "_id";

             if (req.queryParams().contains("sortOrder") && "DESC".equalsIgnoreCase(req.queryParams("sortOrder")))
                 sortBy = "-" + sortBy;

             List<Passenger> allPassenger = getDs().find(Passenger.class).offset(offsetId).limit(count).order(sortBy).asList();

             res.status(200);
             return dataToJson.d2j(allPassenger);
         } catch (Exception e) {
             res.status(500);
             return dataToJson.d2j(new Error(500, 5000, e.getMessage()));
         }
    }

    public String getPassenger(Request req, Response res) throws IOException {
        try {
            String id = req.params(":id");
            Passenger passenger = getDs().find(Passenger.class).field("id").equal(id).get();
            if (passenger == null) {
                res.status(400);
                return dataToJson.d2j(new Error(400, 1004, "Given passenger does not exist"));
            }
            res.status(200);
            return dataToJson.d2j(passenger);
        } catch (Exception e) {
            res.status(500);
            return dataToJson.d2j(new Error(500, 5000, e.getMessage()));
        }
    }

    public String createPassenger(Request req, Response res) throws IOException{
        try{
            JsonObject jsonObject = (JsonObject) new JsonParser().parse(req.body());

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
            if (password.length() > 20 || password.length() < 8) {
                res.status(400);
                return dataToJson.d2j(new Error(400, 2000, "Invalid data type"));
            }

            Passenger newPassenger = new Passenger(firstName, lastName, emailAddress, password, addressLine1, addressLine2,city,state,zip,phoneNumber);

            if (!newPassenger.isValidPassenger()) {
                res.status(400);
                return dataToJson.d2j(new Error(400, 2000, "Invalid data type"));
            }

            getDs().save(newPassenger);
            res.status(200);
            return dataToJson.d2j(newPassenger);

        } catch (Exception e) {
            res.status(500);
            return dataToJson.d2j(new Error(500, 5000, e.getMessage()));
        }
    }

    public String updatePassenger(Request req, Response res) throws IOException {
        try {
            String id = req.params(":id");
            JsonObject jsonObject = (JsonObject) new JsonParser().parse(req.body());

            Passenger passenger =  getDs().find(Passenger.class).field("id").equal(id).get();

            if (jsonObject.has("firstName")) {
                String firstName = jsonObject.get("firstName").toString().replaceAll("\"", "");
                passenger.setFirstName(firstName);
            }

            if (jsonObject.has("lastName")) {
                String lastName = jsonObject.get("lastName").toString().replaceAll("\"", "");
                passenger.setLastName(lastName);
            }

            if (jsonObject.has("emailAddress")) {
                String emailAddress = jsonObject.get("emailAddress").toString().replaceAll("\"", "");
                passenger.setEmailAddress(emailAddress);
            }

            if (jsonObject.has("password")) {
                String password = jsonObject.get("password").toString().replaceAll("\"", "");
                if (password.length() > 20 || password.length() < 8) {
                    res.status(400);
                    return dataToJson.d2j(new Error(400, 2000, "Invalid data type"));
                }
                passenger.setPassword(password);
            }

            if (jsonObject.has("addressLine1")) {
                String addressLine1 = jsonObject.get("addressLine1").toString().replaceAll("\"", "");
                passenger.setAddressLine1(addressLine1);
            }

            if (jsonObject.has("addressLine2")) {
                String addressLine2 = jsonObject.get("addressLine2").toString().replaceAll("\"", "");
                passenger.setAddressLine2(addressLine2);
            }

            if (jsonObject.has("city")) {
                String city = jsonObject.get("city").toString().replaceAll("\"", "");
                passenger.setCity(city);
            }        

            if (jsonObject.has("state")) {
                String state = jsonObject.get("state").toString().replaceAll("\"", "");
                passenger.setState(state);
            }

            if (jsonObject.has("zip")) {
                String zip= jsonObject.get("zip").toString().replaceAll("\"", "");
                passenger.setZip(zip);
            }

            if (jsonObject.has("phoneNumber")) {
                String phoneNumber = jsonObject.get("phoneNumber").toString().replaceAll("\"", "");
                passenger.setPhoneNumber(phoneNumber);
            }

            if (!passenger.isValidPassenger()) {
                res.status(400);
                return dataToJson.d2j(new Error(400, 2000, "Invalid data type"));
            }

            getDs().save(passenger);
            res.status(200);
            return dataToJson.d2j(passenger);
        } catch (Exception e) {
            res.status(500);
            return dataToJson.d2j(new Error(500, 5000, e.getMessage()));
        }

    }

    public String deletePassenger(Request req, Response res) throws IOException {
        try {
            String id = req.params(":id");
            Passenger passenger = getDs().find(Passenger.class).field("id").equal(id).get();
            if (passenger == null) {
                res.status(400);
                return dataToJson.d2j(new Error(400, 1004, "Given passenger does not exist"));
            }
            getDs().delete(passenger);
            res.status(200);
            return dataToJson.d2j(passenger);
        } catch (Exception e) {
            res.status(500);
            return dataToJson.d2j(new Error(500, 5000, e.getMessage()));
        }
    }

    public String getRides(Request req, Response res) throws IOException {
        try {
            String id = req.params(":id");
            Passenger passenger = getDs().find(Passenger.class).field("id").equal(id).get();
            if (passenger == null) {
                res.status(400);
                return dataToJson.d2j(new Error(400, 1004, "Given passenger does not exist"));
            }
            List<String> rideIds = passenger.getRides();
            List<Ride> rides = new ArrayList<>();
            if (rideIds.size() > 0) {
                for (String rideId : rideIds) {
                    Ride ride = getDs().find(Ride.class).field("id").equal(rideId).get();
                    if (ride == null) {
                        res.status(400);
                        return dataToJson.d2j(new Error(400, 1005, "Given ride does not exist"));
                    }
                    rides.add(ride);
                }
            }
            res.status(200);
            return dataToJson.d2j(rides);
        } catch (Exception e) {
            res.status(500);
            return dataToJson.d2j(new Error(500, 5000, e.getMessage()));
        }
    }
}