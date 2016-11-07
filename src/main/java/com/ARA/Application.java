package com.ARA;

import static spark.Spark.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.io.StringWriter;


public class Application {

    private static MorphiaService morphiaService;
    private static CarDAO carDAO;

    public Application() {
        // set up Morphia service
        this.morphiaService = new MorphiaService();
        this.carDAO = new CarDAO(Car.class, morphiaService.getDatastore());
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
    }

    // convert Object data to Json format
    public static String dataToJson(Object data) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            StringWriter sw = new StringWriter();
            mapper.writeValue(sw, data);
            return sw.toString();
        } catch (IOException e){
            throw new RuntimeException("IOException from a StringWriter?");
        }
    }
}