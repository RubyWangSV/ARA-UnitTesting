package com.ARA.DAO;

import com.ARA.module.Car;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import spark.Request;

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
 *
 */
public class CarDAO extends BasicDAO<Car, String> {

    public CarDAO(Class<Car> entityClass, Datastore ds) {
        super(entityClass, ds);
    }

    public List<Car> getAllCars(Request req, Response res) {
        return getDs().find(Car.class).asList();
    }

    public Car getCar(Request req, Response res) {
        String id = req.params(":id");
        return getDs().find(Car.class).field("id").equal(id).get();
    }

    public Car createCar(Request req, Response res) {

        JsonObject jsonObject = (JsonObject) new JsonParser().parse(req.body());

        String make = jsonObject.get("make").toString().replaceAll("\"", "");

        String model = jsonObject.get("model").toString().replaceAll("\"", "");

        String license = jsonObject.get("license").toString().replaceAll("\"", "");

        String carType = jsonObject.get("carType").toString().replaceAll("\"", "");

        Integer maxPassengers = Integer.valueOf(jsonObject.get("maxPassengers").toString());

        String color = jsonObject.get("color").toString().replaceAll("\"", "");

        Type listType = new TypeToken<ArrayList<String>>(){}.getType();
        List<String> validRideTypes = new Gson().fromJson(jsonObject.get("validRideTypes").getAsJsonArray(), listType);

        Car newCar = new Car(make, model, license, carType, maxPassengers, color, validRideTypes);
        getDs().save(newCar);

        return newCar;
    }

    public Car updateCar(Request req, Response res) {
        String id = req.params(":id");
        JsonObject jsonObject = (JsonObject) new JsonParser().parse(req.body());

        Car car =  getDs().find(Car.class).field("id").equal(id).get();

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
            Type listType = new TypeToken<ArrayList<String>>(){}.getType();
            List<String> validRideTypes = new Gson().fromJson(jsonObject.get("validRideTypes").getAsJsonArray(), listType);

            car.setValidRideTypes(validRideTypes);
        }

        getDs().save(car);
        return car;
    }

    public Car deleteCar(Request req, Response res) {
        String id = req.params(":id");
        Car car =  getDs().find(Car.class).field("id").equal(id).get();
        getDs().delete(car);
        return car;
    }
}
