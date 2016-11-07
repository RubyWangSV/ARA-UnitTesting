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
    private static DriverDAO driverDAO;
    private static PassengerDAO passengerDAO;
    private static RideDAO rideDAO;

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

        // CRUD for Cars
        // GET
        // get all cars
        get(versionURI + "/cars", (req, res) -> dataToJson(carDAO.getAllCars()));
        // GET
        // get car with given id
        get(versionURI + "/cars/:id", (req, res) -> dataToJson(carDAO.getCar(req)));
        // POST
        // create a new car
        post(versionURI + "/cars", (req, res) -> dataToJson(carDAO.createCar(req)));
        // POST
        // update car with given id
        patch(versionURI + "/cars/:id", (req, res) -> dataToJson(carDAO.updateCar(req)));
        // DELETE
        // delete car with given id
        delete(versionURI + "/cars/:id", (req, res) -> dataToJson(carDAO.deleteCar(req)));

        // CRUD for Drivers
        get(versionURI + "/drivers", (req, res) -> dataToJson(driverDAO.getAllDrivers()));
        get(versionURI + "/drivers/:id", (req, res) -> dataToJson(driverDAO.getDriver(req)));
        post(versionURI + "/drivers", (req, res) -> dataToJson(driverDAO.createDriver(req)));
        patch(versionURI + "/drivers/:id", (req, res) -> dataToJson(driverDAO.updateDriver(req)));
        delete(versionURI + "/drivers/:id", (req, res) -> dataToJson(driverDAO.deleteDriver(req)));

        // CRUD for Passengers
        get(versionURI + "/passengers", (req, res) -> dataToJson(passengerDAO.getAllPassengers()));
        get(versionURI + "/passengers/:id", (req, res) -> dataToJson(passengerDAO.getPassenger(req)));
        post(versionURI + "/passengers", (req, res) -> dataToJson(passengerDAO.createPassenger(req)));
        patch(versionURI + "/passengers/:id", (req, res) -> dataToJson(passengerDAO.updatePassenger(req)));
        delete(versionURI + "/passengers/:id", (req, res) -> dataToJson(passengerDAO.deletePassenger(req)));


        // CRUD for Rides
        // GET
        // get all rides
        get(versionURI + "/rides", (req, res) -> dataToJson(rideDAO.getAllRides()));
        // GET
        // get ride with given id
        get(versionURI + "/rides/:id", (req, res) -> dataToJson(rideDAO.getRide(req)));
        // POST
        // create a new ride
        post(versionURI + "/rides", (req, res) -> dataToJson(rideDAO.createRide(req)));
        // POST
        // update ride with given id
        post(versionURI + "/rides/:id", (req, res) -> dataToJson(rideDAO.updateRide(req)));
        // DELETE
        // delete ride with given id
        delete(versionURI + "/rides/:id", (req, res) -> dataToJson(rideDAO.deleteRide(req)));
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