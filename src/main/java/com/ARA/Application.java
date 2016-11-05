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
        this.morphiaService = new MorphiaService();
        this.carDAO = new CarDAO(Car.class, morphiaService.getDatastore());
    }

    public static void main(String[] args) {
        String versionURI = "/v1";

        Application app = new Application();

        port(8080);
        get("/", (req, res) -> "Hello, World!");

        // CRUD for Cars
        get(versionURI + "/car", (req, res) -> dataToJson(carDAO.getAllCars()));
        get(versionURI + "/car/:id", (req, res) -> dataToJson(carDAO.getCar(req)));
        post(versionURI + "/car", (req, res) -> dataToJson(carDAO.createCar(req)));
        post(versionURI + "/car/:id", (req, res) -> dataToJson(carDAO.updateCar(req)));
        delete(versionURI + "/car/:id", (req, res) -> dataToJson(carDAO.deleteCar(req)));
    }

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