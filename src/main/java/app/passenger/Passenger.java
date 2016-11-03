package app.passenger;

import java.util.UUID;

public class Passenger {
	UUID id;
	private String make;
	private String model;
	private String license;
	private String carType;
	private int maxPassengers;
	private String color;
	private String validRideTypes; // Values are ECONOMY, PREMIUM, EXECUTIVE
	private Driver driver;

	protected Passenger() {};

	public Passenger(String make, String model, String license, String carType, int maxPassengers, String color, String validRideTypes, Driver driver) {
		////////////
		this.id = UUID.randomUUID();
		this.make = make;
		this.model = model;
		this.license = license;
		this carType = carType;
		this.maxPassengers = maxPassengers;
		this.color = color;
		this.validRideTypes = validRideTypes;
		this.driver = driver;
	}
}
