package com.ARA;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import spark.Spark;

import java.io.IOException;
import java.util.Map;

/**
 * implementation of Driver Test - CRUD
 * @author Edam & Ruby
 * @version 4.0.0
 * @Note: Change required every test run -
 *        1. "emailAddress" in requestBody
 */
public class DriverTest {

    String testDriverID;
    String testDriverXID;
    String token;
    String requestBody = "{" +
            "'firstName':'Bob'," +
            "'lastName':'Azi'," +
            "'emailAddress':'bob50@att.com'," +
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

    @Test
    public void Driver() throws IOException {

        //createDriver
        TestResponse resPost = TestResponse.request("POST", "/v1/drivers", requestBody);
        Map<String, String> jsonPost = resPost.json();
        assertEquals(200, resPost.status);
        assertEquals("Bob", jsonPost.get("firstName"));
        assertNotNull(jsonPost.get("id"));

        testDriverID = jsonPost.get("id");

        //getDriver just created
        TestResponse resGet = TestResponse.request("GET", "/v1/drivers/"+testDriverID+"");
        Map<String, String> jsonGet = resGet.json();
        assertEquals(200, resGet.status);
        assertEquals("Bob", jsonGet.get("firstName"));
        assertNotNull(jsonGet.get("id"));

        //patchDriver
        String requestBodyPatch = "{" +
                "'firstName':'Mark'" +
                "}";
        TestResponse resPatch = TestResponse.request("PATCH", "/v1/drivers/"+testDriverID+"",requestBodyPatch);
        Map<String, String> jsonPatch = resPatch.json();
        assertEquals(200, resPatch.status);
        assertEquals("Mark", jsonPatch.get("firstName"));
        assertNotNull(jsonPatch.get("id"));

        //deleteDriver
        TestResponse resDelete = TestResponse.request("DELETE", "/v1/drivers/"+testDriverID+"");
        Map<String, String> jsonDelete  = resDelete .json();
        assertEquals(200, resDelete.status);
        assertEquals("Mark", jsonDelete .get("firstName"));
        assertNotNull(jsonPatch.get("id"));

    }
}