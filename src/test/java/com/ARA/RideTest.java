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
    String testRideID;
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
            "'routePoints' : [ { " +
            "'timestamp' : 1479360937, " +
            "'latitude' : 123.01," +
            "'longitude' : 12.11" +
            "}, {" +
            "'timestamp' : 1479361000," +
            "'latitude' : 123.01," +
            "'longitude' : 12.11" +
            "}, {" +
            "'timestamp' : 2479361000," +
            "'latitude' : 123.01," +
            "'longitude' : 12.11" +
            "} ]" +
            "}";

    //    String requestBodyRide = String.format("{'rideType' : 'ECONOMY',
//            'startPoint' : [ 123.456, -321.654 ],
//            'endPoint' : [ 12.45, -32.65 ],
//            'requestTime' : '1986-04-08 12:30:21',
//            'pickupTime' : '1999-04-08 12:30:20',
//            'dropOffTime' : '2000-04-08 12:30:11',
//            'status' : 'ARRIVED',
//            'fare' : 123.0,
//            'driver' : '88b0c8e3-b98e-4a02-ba0c-b427eb83121c',
//            'passenger' : '32779739-8b79-4f3b-96e0-e82a599e38f7',
//            'routePoints' : [ {
//        'timestamp' : 1479360937,
//                'latitude' : 123.01,
//                'longitude' : 12.11
//    }, {
//        'timestamp' : 1479361000,
//                'latitude' : 123.01,
//                'longitude' : 12.11
//    }, {
//        'timestamp' : 2479361000,
//                'latitude' : 123.01,
//                'longitude' : 12.11
//    } ]}");

//
//            String.format("{'rideType':[%s],'startPoint':[%f, %f],'endPoint':[%f, %f],'requestTime':[%s],'pickupTime':[%s],'dropOffTime':[%s],'status':[%s],'fare':[%f],'driver':[%s],'passenger':[%s],'routePoints':}", "ECONOMY", 123.456, -321.654, 12.45, -32.65, "1986-04-08 12:30:21",);
//
//
//            "{" +
//            "'rideType' : 'ECONOMY'," +
//            "'startPoint' : [ 123.456, -321.654 ]," +
//            "'endPoint' : [ 12.45, -32.65 ],"+
//            "'requestTime' : '1986-04-08 12:30:21'," +
//            "'' : '1999-04-08 12:30:20'," +
//            "'' : '2000-04-08 12:30:11'," +
//            "'' : 'ARRIVED'," +
//            "'' : 123.0," +
//            "'' : '88b0c8e3-b98e-4a02-ba0c-b427eb83121c'," +
//            "'' : '32779739-8b79-4f3b-96e0-e82a599e38f7'," +
//            "'' : [ {
//                'timestamp' :" + 1479360937 + ",
//                        'latitude' :" + 123.01 +",
//                        'longitude' :" + 12.11 +
//                }, {
//                'timestamp' :" + 1479361000,
//                'latitude' :" + 123.01,
//                'longitude' :" + 12.11
//                }, {
//                'timestamp' :" + 2479361000,
//                'latitude' :"  + 123.01,
//                'longitude' :" 12.11
//                } ]"
//            "}";
//

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

    String testPassengerIDforRide;
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

            //create a ride for the driver
            TestResponse resTokenRide = TestResponse.request("POST", "/v1/drivers/"+testPassengerIDforRide+"/rides", requestBodyRide);
            Map<String, String> jsonTokenRide = resTokenRide.json();
            assertEquals(200, resTokenRide.status);
            assertNotNull(jsonTokenRide.get("id"));

            testRideID = jsonTokenRide.get("id");
    }
}
