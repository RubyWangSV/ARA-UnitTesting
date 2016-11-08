package com.ARA.DAO;

import com.ARA.module.Passenger;

import spark.Request;
import spark.Response;

import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

public class PassengerDAO extends BasicDAO<Passenger, String> {

    public PassengerDAO(Class<Passenger> entityClass, Datastore ds) {
        super(entityClass, ds);
    }

    public List<Passenger> getAllPassengers(Request req, Response res) {
        return getDs().find(Passenger.class).asList();
    }

    public Passenger getPassenger(Request req, Response res) {
        String id = req.params(":id");
        return getDs().find(Passenger.class).field("id").equal(id).get();
    }

    public Passenger createPassenger(Request req, Response res) {
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

        Passenger newPassenger = new Passenger(firstName, lastName, emailAddress, password, addressLine1, addressLine2,city,state,zip,phoneNumber);
        getDs().save(newPassenger);

        return newPassenger;
    }

    public Passenger updatePassenger(Request req, Response res) {
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

        getDs().save(passenger);
        return passenger;
    }

    public Passenger deletePassenger(Request req, Response res) {
        String id = req.params(":id");
        Passenger passenger =  getDs().find(Passenger.class).field("id").equal(id).get();
        getDs().delete(passenger);
        return passenger;
    }
}
