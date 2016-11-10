package com.ARA.util;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.converters.IntegerConverter;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

/**
 * response body when error occurs
 * @author Edam
 *
 */
public class Error {

    public Integer statusCode;
    public Integer errorCode;
    public String errorMessage;

    public Error() {}

    public Error(Integer statusCode, Integer errorCode, String errorMessage) {
        super();
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

}
