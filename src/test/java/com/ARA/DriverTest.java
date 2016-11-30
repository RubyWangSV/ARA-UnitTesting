package com.ARA;


import com.sun.javafx.beans.IDProperty;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import spark.Spark;

import java.io.IOException;
import java.util.Map;

/**
 * implementation of Driver Test
 * @author Edam & Ruby
 * @version 4.0.0
 */
public class DriverTest {

    String testtestID;
    int IDD = 5;
    String requestBody = "{" +
            "'firstName':'Bob'," +
            "'lastName':'Azi'," +
            "'emailAddress':'bob27@att.com'," +
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
    public void createDriver() throws IOException {

        TestResponse res = TestResponse.request("POST", "/v1/drivers", requestBody);
        Map<String, String> json = res.json();
        assertEquals(200, res.status);
        assertEquals("Bob", json.get("firstName"));
        assertNotNull(json.get("id"));

        testtestID = json.get("id");
        IDD = 20;
        System.out.println(testtestID);
    }

    @Test
    public void getDriver() throws IOException {
        System.out.println(IDD);
        System.out.println(testtestID);
        TestResponse res = TestResponse.request("GET", "/v1/drivers/"+testtestID+"");
        Map<String, String> json = res.json();
        assertEquals(200, res.status);
        assertEquals("Bob", json.get("firstName"));
        assertNotNull(json.get("id"));
    }

}

//    @Test
//    public void sessionValidate() {
//        TestResponse res = request("POST", "/v1/sessions,);
//        Map<String, String> json = res.json();
//        assertEquals(200, res.status);
//        System.out.println(json.get("token"));
//    }
//
//

//    @Test
//    public void tryGET() throws IOException {
//        TestResponse res = TestResponse.request("GET", "/v1/drivers");
//        Map<String, String> json = res.json();
//        assertEquals(200, res.status);
//        assertNotNull(json.get("id"));
//    }
//    @Test
//    public void tryPOST() {
//        TestResponse res = request("POST", "/v1/drivers?firstName=zoo&lastName=pia&emailAddress=abc@xyz&password=qqq12344&addressLine1=1&addressLine2=2&city=3&state=4&zip=5&phoneNumber=6&drivingLicense=8&licensedState=10");
//        Map<String, String> json = res.json();
//        assertEquals(200, res.status);
////        assertEquals("zoo", json.get("firstName"));
////        assertEquals("john@foobar.com", json.get("email"));
//        assertNotNull(json.get("id"));
//    }
//
//    @Test
//    public void tryPatch() {
//        TestResponse res = request("PATCH", "/v1/drivers?id=13e52ff9-8bfc-4695-8e2e-a8722a032029&firstName=zooy");
//        Map<String, String> json = res.json();
//        assertEquals(200, res.status);
//        System.out.println(json.get("firstName"));
//        assertEquals("zoo", json.get("firstName"));
////        assertEquals("john@foobar.com", json.get("email"));
//        assertNotNull(json.get("id"));
//    }

