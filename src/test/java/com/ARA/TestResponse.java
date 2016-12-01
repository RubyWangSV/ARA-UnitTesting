package com.ARA;

import com.google.gson.Gson;
import spark.utils.IOUtils;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.fail;

/**
 * implementation of Driver Test
 * @author Edam & Ruby
 * @version 4.0.0
 * Modified from 2https://github.com/mscharhag/blog-examples/blob/master/sparkdemo/src/test/java/com/mscharhag/sparkdemo/UserControllerIntegrationTest.java
 */

public class TestResponse {
    public final String body;
    public final int status;

    public TestResponse(int status, String body) {
        System.out.println(status);
        System.out.println(body);
        this.status = status;
        this.body = body;
    }

    public Map<String,String> json() {
        return new Gson().fromJson(body, HashMap.class);
    }

    public static TestResponse request(String method, String path) {
        try {
            URL url = new URL("http://localhost:8080" + path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setDoOutput(true);
            connection.connect();

            String body = IOUtils.toString(connection.getInputStream());
            return new TestResponse(connection.getResponseCode(), body);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Sending request failed: " + e.getMessage());
            return null;
        }
    }

    public static TestResponse request(String method, String path, String jsonBody) {
        try {

            URL url = new URL("http://localhost:8080" + path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            if (method == "PATCH"){
                connection.setRequestProperty("X-HTTP-Method-Override", "PATCH");
                connection.setRequestMethod("POST");
            }
            else{
                connection.setRequestMethod(method);
            }
            connection.setDoOutput(true);
            connection.connect();
            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(jsonBody);
            wr.flush();

            String body = IOUtils.toString(connection.getInputStream());
            return new TestResponse(connection.getResponseCode(), body);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Sending request failed: " + e.getMessage());
            return null;
        }
    }
}
