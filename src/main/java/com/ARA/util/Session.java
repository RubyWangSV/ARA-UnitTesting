package com.ARA.util;

import com.ARA.DAO.DriverDAO;
import com.ARA.DAO.PassengerDAO;
import com.ARA.module.Driver;
import com.ARA.module.Passenger;
import com.ARA.module.Token;

import com.ARA.util.Error;
import com.ARA.util.MorphiaService;
import com.ARA.util.PasswordEncoder;
import com.ARA.util.dataToJson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.mongodb.morphia.Datastore;
import spark.Request;

import java.io.IOException;
import java.util.Date;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.mongodb.morphia.dao.BasicDAO;
import spark.Response;


/**
 * Session that creates a token with given credentials
 * @author Edam & Ruby
 * @version 2.0.0
 */
public class Session {

    private static MorphiaService morphiaService;
    private static DriverDAO driverDAO;
    private static PassengerDAO passengerDAO;

    // sign JWT with key secret
    private static String key = "thunderbird";

    public Session() {

        this.morphiaService = new MorphiaService();
        this.driverDAO = new DriverDAO(Driver.class, morphiaService.getDatastore());
        this.passengerDAO = new PassengerDAO(Passenger.class, morphiaService.getDatastore());
    }

    /** This method is used to create a token.
     * @param request
     * @param response
     * @return token
     * @throws IOException
     */
    public String createToken(Request request, Response response) throws IOException {
        try {
            JsonObject requestBody = (JsonObject) new JsonParser().parse(request.body());
            String email = requestBody.get("email").toString().replaceAll("\"", "");
            String password = requestBody.get("password").toString().replaceAll("\"", "");
            Driver driver = driverDAO.createQuery()
                    .filter("emailAddress", email)
                    .get();

            Passenger passenger = passengerDAO.createQuery()
                    .filter("emailAddress", email)
                    .get();

            String driverPassword = driver != null ? driver.getPassword() : null;
            String passengerPassword = passenger != null ? passenger.getPassword() : null;

            PasswordEncoder pe = new PasswordEncoder();

            boolean isDriver = pe.validatePassword(password, driverPassword);
            boolean isPassenger = pe.validatePassword(password, passengerPassword);

            if (!isDriver && !isPassenger) {
                response.status(400);
                return dataToJson.d2j(new Error(400, 9001, "Wrong username or password"));
            }

            String userId = (driver != null) ? driver.getId() : passenger.getId();

            // the JWT signature algorithm will be using to sign the token
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

            long nowMillis = System.currentTimeMillis();
            Date now = new Date(nowMillis);

            // set the JWT Claims, userId as body
            JwtBuilder builder = Jwts.builder()
                    .setIssuedAt(now)
                    .setSubject(userId)
                    .signWith(signatureAlgorithm, key);

            // token will expired in 10 min
            long ttlMillis = 10 * 60 * 1000;
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);

            String tokenString = builder.compact();

            Token token = new Token(tokenString);

            response.status(200);
            return dataToJson.d2j(token);

        } catch (Exception e) {
            response.status(500);
            return dataToJson.d2j(new Error(500, 5000, e.getMessage()));
        }
    }
}