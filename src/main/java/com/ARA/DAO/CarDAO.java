package com.ARA.DAO;

import com.ARA.module.Car;

import com.ARA.module.Driver;
import com.ARA.util.Error;
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
 * implementation of Car's Data Access Object
 * @author Edam
 * @version 2.0.0
 */
public class CarDAO extends BasicDAO<Car, String> {

    public CarDAO(Class<Car> entityClass, Datastore ds) {
        super(entityClass, ds);
    }

    /** This method is used to get all cars.
     * @param request
     * @param response
     * @return all cars
     * @throws IOException
     */
    public String getAllCars(Request request, Response response) throws IOException {
        try {
            List<Car> allCar = getDs().find(Car.class).asList();
            response.status(200);
            return dataToJson.d2j(allCar);
        } catch (Exception e) {
            response.status(500);
            return dataToJson.d2j(new Error(500, 5000, e.getMessage()));
        }
    }

    /** This method is used to get a specific car with :id.
     * @param request
     * @param response
     * @return The car specified with :id.
     * @throws IOException
     */
    public String getCar(Request request, Response response) throws IOException {
        try {
            String id = request.params(":id");
            Car car = getDs().find(Car.class).field("id").equal(id).get();
            if (car == null) {
                response.status(400);
                return dataToJson.d2j(new Error(400, 1002, "Given car does not exist"));
            }
            response.status(200);
            return dataToJson.d2j(car);
        } catch (Exception e) {
            response.status(500);
            return dataToJson.d2j(new Error(500, 5000, e.getMessage()));
        }
    }

    /** This method is used to update a specific car with :id.
     * @param request
     * @param response
     * @return The car specified with :id.
     * @throws IOException
     */    
    public String updateCar(Request request, Response response) throws IOException {
        try {
            String id = request.params(":id");
            JsonObject jsonObject = (JsonObject) new JsonParser().parse(request.body());

            Car car = getDs().find(Car.class).field("id").equal(id).get();

            if (jsonObject.has("make")) {
                String make = jsonObject.get("make").toString().replaceAll("\"", "");

                car.setMake(make);
            }

            if (jsonObject.has("model")) {
                String model = jsonObject.get("model").toString().replaceAll("\"", "");

                car.setModel(model);
            }

            if (jsonObject.has("license")) {
                String license = jsonObject.get("license").toString().replaceAll("\"", "");

                car.setLicense(license);
            }

            if (jsonObject.has("carType")) {
                String carType = jsonObject.get("carType").toString().replaceAll("\"", "");

                car.setCarType(carType);
            }

            if (jsonObject.has("maxPassengers")) {
                Integer maxPassengers = Integer.valueOf(jsonObject.get("maxPassengers").toString());

                car.setMaxPassengers(maxPassengers);
            }

            if (jsonObject.has("color")) {
                String color = jsonObject.get("color").toString().replaceAll("\"", "");

                car.setColor(color);
            }

            if (jsonObject.has("validRideTypes")) {
                Type listType = new TypeToken<ArrayList<String>>() {
                }.getType();
                List<String> validRideTypes = new Gson().fromJson(jsonObject.get("validRideTypes").getAsJsonArray(), listType);

                car.setValidRideTypes(validRideTypes);
            }
            if (!car.isValidCar()) {
                response.status(400);
                return dataToJson.d2j(new Error(400, 2000, "Invalid data type"));
            }

            getDs().save(car);
            response.status(200);
            return dataToJson.d2j(car);
        } catch (Exception e) {
            response.status(500);
            return dataToJson.d2j(new Error(500, 5000, e.getMessage()));
        }
    }

    /** This method is used to delete a specific car with :id.
     * @param request
     * @param response
     * @return The car specified with :id.
     * @throws IOException
     */    
    public String deleteCar(Request request, Response response) throws IOException {
        try {
            String id = request.params(":id");
            Car car = getDs().find(Car.class).field("id").equal(id).get();
            if (car == null) {
                response.status(400);
                return dataToJson.d2j(new Error(400, 1002, "Given car does not exist"));
            }
            getDs().delete(car);
            response.status(200);
            return dataToJson.d2j(car);
        } catch (Exception e) {
            response.status(500);
            return dataToJson.d2j(new Error(500, 5000, e.getMessage()));
        }
    }

    /** This method is used to get the driver info of a specific car with :id.
     * @param request
     * @param response
     * @return The driver info of a specific car with :id.
     * @throws IOException
     */    
    public String getDriver(Request request, Response response) throws IOException {
        try {
            String id = request.params(":id");
            Car car = getDs().find(Car.class).field("id").equal(id).get();
            if (car == null) {
                response.status(400);
                return dataToJson.d2j(new Error(400, 1002, "Given car does not exist"));
            }
            List<String> driverIds = car.getDrivers();
            List<Driver> drivers = new ArrayList<>();
            if (driverIds.size() > 0) {
                for (String driverId : driverIds) {
                    Driver driver = getDs().find(Driver.class).field("id").equal(driverId).get();
                    if (driver == null) {
                        response.status(400);
                        return dataToJson.d2j(new Error(400, 1002, "Given driver does not exist"));
                    }
                    drivers.add(driver);
                }
            }
            response.status(200);
            return dataToJson.d2j(drivers);
        } catch (Exception e) {
            response.status(500);
            return dataToJson.d2j(new Error(500, 5000, e.getMessage()));
        }
    }
}
