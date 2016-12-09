package com.ARA;

import org.junit.Test;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
/**
 * implementation of Ride Test - CRUD
 * @author Edam & Ruby
 * @version 4.0.0
 * @Note: 1. Mark out email duplicate verification in DriverDAO 123-127
 *        2. Mark out toke verification (PassengerDAO-)
 */
public class RideTest {

    String testDriverIDforRide;
    String testPassengerIDforRide;
    String testRideID;
    String testCarID;
    String tokenDriver;
    String tokenPassenger;

    String requestBodyRide = "{" +
            "'rideType' : 'ECONOMY'," +
            "'startPoint' : [ 123.456, -321.654 ]," +
            "'endPoint' : [ 12.45, -32.65 ]," +
            "'requestTime' : '1986-04-08 12:30:21'," +
            "'pickupTime' : '1999-04-08 12:30:20'," +
            "'dropOffTime' : '2000-04-08 12:30:11'," +
            "'status' : 'ARRIVED'," +
            "'fare' : 123.0," +
            "'driver' : '88b0c8e3-b98e-4a02-ba0c-b427eb83121c'," +
            "'passenger' : '32779739-8b79-4f3b-96e0-e82a599e38f7'," +
            "'car' : '2d8dd17a-e1e6-44e8-8fd6-4032ce35f402'," +
            "'routePoints' : ''" +
            "}";

    String requestBodyRoutePoint = "{"+
                "'timestamp' : 1479361000, " +
                "'latitude' : 125.01," +
                "'longitude' : 12.11" +
                "}";

    String requestBodyDriver = "{" +
            "'firstName':'Mark'," +
            "'lastName':'Azi'," +
            "'emailAddress':'RideTestDriver01@att.com'," +
            "'password':'1234567890'," +
            "'addressLine1':'120 El, CA'," +
            "'addressLine2':''," +
            "'city':'MV'," +
            "'state':'CA'," +
            "'zip':'99900'," +
            "'phoneNumber':'333-999-0000'," +
            "'drivingLicense':'X7890'," +
            "'licensedState':'CA'" +
            "}";

    String requestBodyDriverToken = "{" +
            "'email':'RideTestDriver01@att.com'," +
            "'password':'1234567890'" +
            "}";

    String requestBodyCar = "{" +
            "'make':'Tesla'," +
            "'model':'S'," +
            "'license':'12345'," +
            "'carType':'sedan'," +
            "'maxPassengers':10," +
            "'color':'White'," +
            "'validRideTypes':  [ \"EXECUTIVE\" ]" +
            "}";

    String requestBodyPassenger = "{" +
            "'firstName':'Zoe'," +
            "'lastName':'Moore'," +
            "'emailAddress':'RideTestPassenger01@att.com'," +
            "'password':'1234567890'," +
            "'addressLine1':'1999 Castro Street'," +
            "'addressLine2':''," +
            "'city':'MV'," +
            "'state':'CA'," +
            "'zip':'72033'," +
            "'phoneNumber':'546-777-8989'" +
            "}";

    String requestBodyPassegnerToken = "{" +
            "'email':'RideTestPassenger01@att.com'," +
            "'password':'1234567890'" +
            "}";

    @Test
    public void Ride() throws IOException {
            //create a driver for token and ride
            TestResponse resPostX = TestResponse.request("POST", "/v1/drivers", requestBodyDriver);
            Map<String, String> jsonPostX = resPostX.json();
            assertEquals(200, resPostX.status);
            assertEquals("Mark", jsonPostX.get("firstName"));
            assertEquals("Azi", jsonPostX.get("lastName"));
            assertEquals("120 El, CA", jsonPostX.get("addressLine1"));
            assertEquals("", jsonPostX.get("addressLine2"));
            assertEquals("MV", jsonPostX.get("city"));
            assertEquals("CA", jsonPostX.get("state"));
            assertEquals("99900", jsonPostX.get("zip"));
            assertEquals("333-999-0000", jsonPostX.get("phoneNumber"));
            assertEquals("X7890", jsonPostX.get("drivingLicense"));
            assertEquals("CA", jsonPostX.get("licensedState"));
            assertNotNull(jsonPostX.get("id"));
            testDriverIDforRide = jsonPostX.get("id");

            //get token for the driver
            TestResponse resToken = TestResponse.request("POST", "/v1/sessions", requestBodyDriverToken);
            Map<String, String> jsonToken = resToken.json();
            assertEquals(200, resToken.status);
            assertNotNull(jsonToken.get("token"));

            tokenDriver = jsonToken.get("token");

//            //create a car for the driver
//            TestResponse resTokenCar = TestResponse.request("POST", "/v1/drivers/"+testDriverIDforRide+"/cars?token="+tokenDriver+"", requestBodyCar);
//            Map<String, String> jsonTokenCar = resTokenCar.json();
//            assertEquals(200, resTokenCar.status);
//            assertEquals("Tesla", jsonTokenCar.get("make"));
//            assertEquals("S", jsonTokenCar.get("model"));
//            assertEquals("12345", jsonTokenCar.get("license"));
//            assertEquals("sedan", jsonTokenCar.get("carType"));
//            assertEquals("White", jsonTokenCar.get("color"));
//            assertEquals(Double.valueOf(10), jsonTokenCar.get("maxPassengers"));
//            assertEquals(Arrays.asList("EXECUTIVE"), jsonTokenCar.get("validRideTypes"));
//            assertNotNull(jsonTokenCar.get("id"));

//            testCarID = jsonTokenCar.get("id");

            //create a Passenger for token and ride
            TestResponse resPostPassenger = TestResponse.request("POST", "/v1/passengers", requestBodyPassenger);
            Map<String, String> jsonPostPassenger = resPostPassenger.json();
            assertEquals(200, resPostPassenger.status);
            assertEquals("Zoe", jsonPostPassenger.get("firstName"));
            assertEquals("Moore", jsonPostPassenger.get("lastName"));
            assertEquals("1999 Castro Street", jsonPostPassenger.get("addressLine1"));
            assertEquals("", jsonPostPassenger.get("addressLine2"));
            assertEquals("MV", jsonPostPassenger.get("city"));
            assertEquals("CA", jsonPostPassenger.get("state"));
            assertEquals("72033", jsonPostPassenger.get("zip"));
            assertEquals("546-777-8989", jsonPostPassenger.get("phoneNumber"));
            assertNotNull(jsonPostPassenger.get("id"));

            testPassengerIDforRide = jsonPostPassenger.get("id");

            //get token for the passenger
            TestResponse resTokenPassegner = TestResponse.request("POST", "/v1/sessions", requestBodyPassegnerToken);
            Map<String, String> jsonTokenPassegner = resTokenPassegner.json();
            assertEquals(200, resTokenPassegner.status);
            assertNotNull(jsonTokenPassegner.get("token"));

            tokenPassenger = jsonTokenPassegner.get("token");

            //create a ride for the passenger
            TestResponse resTokenRide = TestResponse.request("POST", "/v1/passengers/"+testPassengerIDforRide+"/rides", requestBodyRide);
            Map<String, String> jsonTokenRide = resTokenRide.json();
            assertEquals(200, resTokenRide.status);
            assertNotNull(jsonTokenRide.get("id"));

            testRideID = jsonTokenRide.get("id");

            //update the driver and the car to the ride
            String requestBodyRideInfo = "{" +
                        "'driver':" + testDriverIDforRide + "," +
                        "'car':" + "2d8dd17a-e1e6-44e8-8fd6-4032ce35f402" +
                    "}";

            TestResponse resTokenRideUpdate = TestResponse.request("PATCH", "/v1/rides/" + testRideID +"", requestBodyRideInfo);
            Map<String, String> jsonTokenRideUpdate = resTokenRideUpdate.json();
            assertEquals(200, resTokenRideUpdate.status);
            assertNotNull(jsonTokenRideUpdate.get("id"));

            //create routePoints
            TestResponse resRoutePoints = TestResponse.request("POST", "/v1/rides/" + testRideID +"/routePoints", requestBodyRoutePoint);
            Map<String, String> jsonRoutePoints = resRoutePoints.json();
            assertEquals(200, resRoutePoints.status);

            //Get routePoints
            TestResponse resRoutePointsGet = TestResponse.request("GET", "/v1/rides/" + testRideID +"/routePoints");
            assertEquals(200, resRoutePointsGet.status);

            //Get latest routePoints
            TestResponse resLatestRoutePointsGet = TestResponse.request("GET", "/v1/rides/" + testRideID +"/routePoints/latest");
            assertEquals(200, resLatestRoutePointsGet.status);

    }
}
