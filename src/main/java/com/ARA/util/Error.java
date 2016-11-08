package com.ARA.util;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.converters.IntegerConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * response body when error occurs
 * @author Edam
 *
 */
public class Error {

    private Integer statusCode;
    private Integer errorCode;
    private String errorMessage;

    public Error() {}

    /**
     * full constructor
     *
     * @param statusCode
     * @param errorCode
     * @param errorMessage
     */
    public Error(Integer statusCode, Integer errorCode, String errorMessage) {
        super();
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

}
