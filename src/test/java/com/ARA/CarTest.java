package com.ARA;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * implementation of Car Test - CRUD
 * @author Edam & Ruby
 * @version 4.0.0
 * @Note: Need to change the carID every time for now --> use token
 */

public class CarTest {
    String testCarID = "ae62bb9c-fefd-4607-ae6c-eb21e12f7b83";
    String testCarNGID = "xxxID";
    String requestBody = "{" +
            "'make':'Tesla'," +
            "'model':'S'," +
            "'license':'12345'," +
            "'carType':'sedan'," +
            "'maxPassengers':10," +
            "'color':'White'," +
            "'validRideTypes':  [ \"ECONOMY\", \"PREMIUM\", \"EXECUTIVE\" ]" +
            "}";
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void Car() throws IOException {
        //Please see token test for createCar

        //getCar just created
        TestResponse resGet = TestResponse.request("GET", "/v1/cars/"+testCarID+"");
        Map<String, String> jsonGet = resGet.json();
        assertEquals(200, resGet.status);
        assertEquals("Tesla", jsonGet.get("make"));
        assertNotNull(jsonGet.get("id"));

        //patchCar
        String requestBodyPatch = "{" +
                "'make':'Honda'" +
                "}";
        TestResponse resPatch = TestResponse.request("PATCH", "/v1/cars/"+testCarID+"",requestBodyPatch);
        Map<String, String> jsonPatch = resPatch.json();
        assertEquals(200, resPatch.status);
        assertEquals("Honda", jsonPatch.get("make"));
        assertNotNull(jsonPatch.get("id"));

        //patchCar - Change back to Tesla
        String requestBodyPatch2 = "{" +
                "'make':'Tesla'" +
                "}";
        TestResponse resPatch2 = TestResponse.request("PATCH", "/v1/cars/"+testCarID+"",requestBodyPatch2);
        Map<String, String> jsonPatch2 = resPatch2.json();
        assertEquals(200, resPatch2.status);
        assertEquals("Tesla", jsonPatch2.get("make"));
        assertNotNull(jsonPatch2.get("id"));

        //deleteCar
        TestResponse resDelete = TestResponse.request("DELETE", "/v1/cars/"+testCarID+"");
        Map<String, String> jsonDelete  = resDelete .json();
        assertEquals(200, resDelete.status);
        assertEquals("Tesla", jsonDelete .get("make"));
        assertNotNull(jsonPatch.get("id"));

        //should not delete car
        TestResponse resDeleteNG = TestResponse.request("DELETE", "/v1/cars/"+testCarNGID+"");
        exception.expect(IndexOutOfBoundsException.class);

        //should not get car
        TestResponse resGetNG = TestResponse.request("GET", "/v1/cars/"+testCarNGID+"");
        exception.expect(IndexOutOfBoundsException.class);
    }
}
