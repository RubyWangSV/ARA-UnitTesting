package com.ARA;

import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * implementation of Token Test - 1. driver's car with token 2. update ride with token
 * @author Edam & Ruby
 * @version 4.0.0
 * @Note: Change required every test run -
 *        1. "emailAddress" in requestBodyX
 *        2. "email" in requestBodyToken (email & password need to match requestBodyX)
 */
public class TokenTest {

    String testDriverXID;
    String token;

    String requestBodyX = "{" +
            "'firstName':'Mark'," +
            "'lastName':'Azi'," +
            "'emailAddress':'mark69@att.com'," +
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
            "'email':'mark69@att.com'," +
            "'password':'1234567890'" +
            "}";

    String requestBodyCar = "{" +
            "'make':'Tesla'," +
            "'model':'S'," +
            "'license':'12345'," +
            "'carType':'coupe'," +
            "'maxPassengers':2," +
            "'color':'White'," +
            "'validRideTypes': [ \"ECONOMY\"]" +
            "}";

    @Test
    public void Token() throws IOException {

        //create a driver for token use
        TestResponse resPostX = TestResponse.request("POST", "/v1/drivers", requestBodyX);
        Map<String, String> jsonPostX = resPostX.json();
        assertEquals(200, resPostX.status);
        assertEquals("Mark", jsonPostX.get("firstName"));
        assertNotNull(jsonPostX.get("id"));
        testDriverXID = jsonPostX.get("id");

        //get token for the driver
        TestResponse resToken = TestResponse.request("POST", "/v1/sessions", requestBodyToken);
        Map<String, String> jsonToken = resToken.json();
        assertEquals(200, resToken.status);

        token = jsonToken.get("token");

        //create a car for the driver
        TestResponse resTokenCar = TestResponse.request("POST", "/v1/drivers/"+testDriverXID+"/cars?token="+token+"", requestBodyCar);
        Map<String, String> jsonTokenCar = resTokenCar.json();
        assertEquals(200, resToken.status);
    }
}