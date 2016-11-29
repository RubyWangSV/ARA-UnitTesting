package com.ARA.test;

import com.ARA.Application;
import com.ARA.module.Car;
import static com.ARA.util.dataToJson.d2j;
import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import spark.Spark;

import java.io.IOException;
import java.util.Map;

/**
 * implementation of Driver Test
 * @author Edam & Ruby
 * @version 4.0.0
 */

public class CarTest {

    @BeforeClass
    public static void beforeClass() {
        Application.main(null);
    }

    @AfterClass
    public static void afterClass() {
        Spark.stop();
    }

    @Test
    public void createCar() throws IOException {
//        Car testCar = new Car("test","test","test","test",12345,"test",["ECONOMY", "PREMIUM", "EXECUTIVE" ]);
//
//        TestResponse response = TestResponse.request("POST", "/cars", d2j(testCar));
//        Map<String, String> json = response.json();
//        assertEquals(201, response.status);
//        assertEquals("test", json.get("name"));
//        assertEquals("test", json.get("license"));
    }
}
