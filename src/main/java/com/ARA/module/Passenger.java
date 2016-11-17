package com.ARA.module;

import com.ARA.util.PasswordEncoder;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("passenger")
@JsonIgnoreProperties({"validPassenger", "password"})
public class Passenger {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String password;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String zip;           
    private String phoneNumber;

    private List<String> rides;
    /**
     * keep an empty constructor so that morphia
     * can recreate this entity fetch it from
     * the database
     */
    public Passenger(){}


    /**
     * full constructor
     *
     * @param firstName
     * @param lastName
     * @param emailAddress
     * @param password
     * @param addressLine1
     * @param addressLine2
     * @param city
     * @param state
     * @param zip
     * @param phoneNumber
     */

    public Passenger(String firstName, String lastName, String emailAddress, String password, String addressLine1, String addressLine2, String city, String state, String zip, String phoneNumber) {
        super();
        this.id = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.password = new PasswordEncoder().encode(password);
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;   
        this.state = state;
        this.zip = zip;
        this.phoneNumber = phoneNumber;            
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = new PasswordEncoder().encode(password);
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getaddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }    

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }    

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public List<String> getRides() {
        return rides;
    }

    public boolean isValidPassenger () {
        if (firstName.length() > 50 || lastName.length() > 50 || emailAddress.length() > 50
                || addressLine1.length() > 100 || addressLine2.length() > 100
                || city.length() > 50 || state.length() != 2 || zip.length() != 5)
            return false;
        Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailAddress);
        if (! matcher.find())
            return false;

        return true;
    }
}
