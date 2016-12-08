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
 * @Note: 1. Mark out email duplicate verification
 *        2. Mark out toke verification (PassengerDAO-)
 */
public class RideTest {

    String testDriverYID;
    String token;
//    String requestBodyRide = "{" +
//            "'rideType' : 'ECONOMY'," +
//            "'startPoint' : [ 123.456, -321.654 ]," +
//            "'endPoint' : [ 12.45, -32.65 ],"+
//            "'requestTime' : '1986-04-08 12:30:21'," +
//            "'pickupTime' : '1999-04-08 12:30:20'," +
//            "'dropOffTime' : '2000-04-08 12:30:11'," +
//            "'status' : 'ARRIVED'," +
//            "'fare' : 123.0," +
//            "'driver' : '88b0c8e3-b98e-4a02-ba0c-b427eb83121c'," +
//            "'passenger' : '32779739-8b79-4f3b-96e0-e82a599e38f7'," +
//            "'routePoints' : [ {
//                'timestamp' : 1479360937,
//                        'latitude' : 123.01,
//                        'longitude' : 12.11
//                }, {
//                'timestamp' : 1479361000,
//                'latitude' : 123.01,
//                'longitude' : 12.11
//                }, {
//                'timestamp' : 2479361000,
//                'latitude' : 123.01,
//                'longitude' : 12.11
//                } ]" +
//            "}";

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

    String requestBodyToken = "{" +
            "'email':'RideTestDriver01@att.com'," +
            "'password':'1234567890'" +
            "}";

//    @Test
//    public void Ride() throws IOException {
//            //create a driver for token use
//            TestResponse resPostX = TestResponse.request("POST", "/v1/drivers", requestBodyDriver);
//            Map<String, String> jsonPostX = resPostX.json();
//            assertEquals(200, resPostX.status);
//            assertEquals("Mark", jsonPostX.get("firstName"));
//            assertEquals("Azi", jsonPostX.get("lastName"));
//            assertEquals("120 El, CA", jsonPostX.get("addressLine1"));
//            assertEquals("", jsonPostX.get("addressLine2"));
//            assertEquals("MV", jsonPostX.get("city"));
//            assertEquals("CA", jsonPostX.get("state"));
//            assertEquals("99900", jsonPostX.get("zip"));
//            assertEquals("333-999-0000", jsonPostX.get("phoneNumber"));
//            assertEquals("X7890", jsonPostX.get("drivingLicense"));
//            assertEquals("CA", jsonPostX.get("licensedState"));
//            assertNotNull(jsonPostX.get("id"));
//            testDriverXID = jsonPostX.get("id");
//
//            //get token for the driver
//            TestResponse resToken = TestResponse.request("POST", "/v1/sessions", requestBodyToken);
//            Map<String, String> jsonToken = resToken.json();
//            assertEquals(200, resToken.status);
//            assertNotNull(jsonToken.get("token"));
//
//            token = jsonToken.get("token");

//            //create a ride for the driver
//            TestResponse resTokenRide = TestResponse.request("POST", "/v1/drivers/"+testDriverYID+"", requestBodyRide);
//            Map<String, String> jsonTokenCar = resTokenCar.json();
//            assertEquals(200, resTokenCar.status);
//            assertEquals("Tesla", jsonTokenCar.get("make"));
//            assertEquals("S", jsonTokenCar.get("model"));
//            assertEquals("12345", jsonTokenCar.get("license"));
//            assertEquals("sedan", jsonTokenCar.get("rideType"));
//            assertEquals("White", jsonTokenCar.get("color"));
//            assertEquals(Double.valueOf(10), jsonTokenCar.get("maxPassengers"));
//            assertEquals(Arrays.asList("EXECUTIVE"), jsonTokenCar.get("validRideTypes"));
//            assertNotNull(jsonTokenCar.get("id"));
//
//            testCarID = jsonTokenCar.get("id");
//  }

}
