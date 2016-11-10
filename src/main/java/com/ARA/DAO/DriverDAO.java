package com.ARA.DAO;

import com.ARA.module.Driver;

import com.ARA.util.Error;
import com.ARA.util.Validate;
import com.ARA.util.dataToJson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import spark.Request;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.ArrayList;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import spark.Response;


/**
 * implementation of Driver's Data Access Object
 * @author Ruby
 *
 */
public class DriverDAO extends BasicDAO<Driver, String> {

    public DriverDAO(Class<Driver> entityClass, Datastore ds) {
        super(entityClass, ds);
    }

    public String getAllDrivers(Request req, Response res) throws IOException{
        try {
            List<Driver> allDriver = getDs().find(Driver.class).asList();
            res.status(200);
            return dataToJson.d2j(allDriver);
        } catch (Exception e) {
            res.status(500);
            return dataToJson.d2j(new Error(500, 5000, e.getMessage()));
        }
    }

    public String getDriver(Request req, Response res) throws IOException {
        try {
            String id = req.params(":id");
            Driver driver = getDs().find(Driver.class).field("id").equal(id).get();
            if (driver == null) {
                res.status(400);
                return dataToJson.d2j(new Error(400, 1000, "Driver not found"));
            }
            res.status(200);
            return dataToJson.d2j(driver);
        } catch (Exception e) {
            res.status(500);
            return dataToJson.d2j(new Error(500, 5000, e.getMessage()));
        }
    }

    public String createDriver(Request req, Response res) throws IOException{
        try{
            JsonObject jsonObject = (JsonObject) new JsonParser().parse(req.body());
            boolean valid = true;
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

            Driver newDriver = new Driver(firstName, lastName, emailAddress, password, addressLine1, addressLine2,city,state,zip,phoneNumber,drivingLicense,licensedState);

            if (!Validate.validDriver(newDriver)) {
                res.status(400);
                return dataToJson.d2j(new Error(400, 2000, "Invalid data type - Length"));
            }

            getDs().save(newDriver);
            res.status(200);
            return dataToJson.d2j(newDriver);

        } catch (Exception e) {
            res.status(500);
            return dataToJson.d2j(new Error(500, 5000, e.getMessage()));
        }
    }

    public String updateDriver(Request req, Response res) throws IOException {
        try {
            String id = req.params(":id");
            JsonObject jsonObject = (JsonObject) new JsonParser().parse(req.body());

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
                driver.setEmailAddress(emailAddress);
            }

            if (jsonObject.has("password")) {
                String password = jsonObject.get("password").toString().replaceAll("\"", "");
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

            if (!Validate.validDriver(driver)) {
                res.status(400);
                return dataToJson.d2j(new Error(400, 2000, "Invalid data type"));
            }

            getDs().save(driver);
            res.status(200);
            return dataToJson.d2j(driver);
        } catch (Exception e) {
            res.status(500);
            return dataToJson.d2j(new Error(500, 5000, e.getMessage()));
        }

    }

    public String deleteDriver(Request req, Response res) throws IOException{
        try {
            String id = req.params(":id");
            Driver driver = getDs().find(Driver.class).field("id").equal(id).get();
            if (driver == null) {
                res.status(400);
                return dataToJson.d2j(new Error(400, 1000, "Driver not found"));
            }
            getDs().delete(driver);
            res.status(200);
            return dataToJson.d2j(driver);
        } catch (Exception e) {
            res.status(500);
            return dataToJson.d2j(new Error(500, 5000, e.getMessage()));
        }
    }
}