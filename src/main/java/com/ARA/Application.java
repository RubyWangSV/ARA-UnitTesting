package com.ARA;

import static spark.Spark.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;

import java.io.IOException;
import java.io.StringWriter;


public class Application {

    private static MorphiaService morphiaService;
    private static CarDAO carDAO;
    private static RideDAO rideDAO;

    public Application() {
        // set up Morphia service
        this.morphiaService = new MorphiaService();
        this.carDAO = new CarDAO(Car.class, morphiaService.getDatastore());
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

        // CRUD for Cars
        // GET
        // get all cars
        get(versionURI + "/car", (req, res) -> dataToJson(carDAO.getAllCars()));
        // GET
        // get car with given id
        get(versionURI + "/car/:id", (req, res) -> dataToJson(carDAO.getCar(req)));
        // POST
        // create a new car
        post(versionURI + "/car", (req, res) -> dataToJson(carDAO.createCar(req)));
        // POST
        // update car with given id
        post(versionURI + "/car/:id", (req, res) -> dataToJson(carDAO.updateCar(req)));
        // DELETE
        // delete car with given id
        delete(versionURI + "/car/:id", (req, res) -> dataToJson(carDAO.deleteCar(req)));

        // CRUD for Rides
        // GET
        // get all rides
        get(versionURI + "/ride", (req, res) -> dataToJson(rideDAO.getAllRides()));
        // GET
        // get ride with given id
        get(versionURI + "/ride/:id", (req, res) -> dataToJson(rideDAO.getRide(req)));
        // POST
        // create a new ride
        post(versionURI + "/ride", (req, res) -> dataToJson(rideDAO.createRide(req)));
        // POST
        // update ride with given id
        post(versionURI + "/ride/:id", (req, res) -> dataToJson(rideDAO.updateRide(req)));
        // DELETE
        // delete ride with given id
        delete(versionURI + "/ride/:id", (req, res) -> dataToJson(rideDAO.deleteRide(req)));
    }

    // convert Object data to Json format
    public static String dataToJson(Object data) throws IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JSR310Module());;
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            StringWriter sw = new StringWriter();
            mapper.writeValue(sw, data);
            return sw.toString();
        } catch (IOException e){
            throw new RuntimeException("IOException from a StringWriter?");
        }
    }
}