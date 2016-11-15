package com.ARA;

import com.ARA.module.*;
import com.ARA.DAO.*;
import com.ARA.util.*;

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
        get(versionURI + "/cars", (req, res) -> carDAO.getAllCars(req, res));
        // GET
        // get car with given id
        get(versionURI + "/cars/:id", (req, res) -> carDAO.getCar(req, res));
        // POST
        // create a new car
        post(versionURI + "/cars", (req, res) -> carDAO.createCar(req, res));
        // POST
        // update car with given id
        patch(versionURI + "/cars/:id", (req, res) -> carDAO.updateCar(req, res));
        // DELETE
        // delete car with given id
        delete(versionURI + "/cars/:id", (req, res) -> carDAO.deleteCar(req, res));

        // CRUD for Drivers
        get(versionURI + "/drivers", (req, res) -> driverDAO.getAllDrivers(req, res));
        get(versionURI + "/drivers/:id", (req, res) -> driverDAO.getDriver(req, res));
        post(versionURI + "/drivers", (req, res) -> driverDAO.createDriver(req, res));
        patch(versionURI + "/drivers/:id", (req, res) -> driverDAO.updateDriver(req, res));
        delete(versionURI + "/drivers/:id", (req, res) -> driverDAO.deleteDriver(req, res));
        get(versionURI + "/drivers/:id/cars", (req, res) -> driverDAO.getCars(req, res));
        post(versionURI + "/drivers/:id/cars", (req, res) -> driverDAO.createCar(req, res));
        get(versionURI + "/drivers/:id/rides", (req, res) -> driverDAO.getRides(req, res));
//
//        // CRUD for Passengers
//        get(versionURI + "/passengers", (req, res) -> passengerDAO.getAllPassengers(req, res));
//        get(versionURI + "/passengers/:id", (req, res) -> passengerDAO.getPassenger(req, res));
//        post(versionURI + "/passengers", (req, res) -> passengerDAO.createPassenger(req, res));
//        patch(versionURI + "/passengers/:id", (req, res) -> passengerDAO.updatePassenger(req, res));
//        delete(versionURI + "/passengers/:id", (req, res) -> passengerDAO.deletePassenger(req, res));
//
//        // CRUD for Rides
//        // GET
//        // get all rides
//        get(versionURI + "/rides", (req, res) -> rideDAO.getAllRides(req, res));
//        // GET
//        // get ride with given id
//        get(versionURI + "/rides/:id", (req, res) -> rideDAO.getRide(req, res));
//        // POST
//        // create a new ride
//        post(versionURI + "/rides", (req, res) -> rideDAO.createRide(req, res));
//        // POST
//        // update ride with given id
//        post(versionURI + "/rides/:id", (req, res) -> rideDAO.updateRide(req, res));
//        // DELETE
//        // delete ride with given id
//        delete(versionURI + "/rides/:id", (req, res) -> rideDAO.deleteRide(req, res));
    }

}