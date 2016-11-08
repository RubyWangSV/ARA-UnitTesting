package com.ARA.module;

import java.util.List;
import java.util.UUID;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("passenger")
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
        this.password = password;       
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
        this.password = password;
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
}
