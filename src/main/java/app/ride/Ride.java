package app.ride;

import java.util.UUID;

public class Ride {
	UUID id;
	private String passenger;
	private String driver;
	private String car;
	private String rideType;
	private String startPoint;
	private String endPoint;
	private String requestTime;
	private String pickupTime;
	private String dropOffTime;
	private String status;
	private int fare;
	private String route;

	protected Ride() {};

	public Ride(String passenger, String driver, String car, String rideType, String startPoint, String endPoint, String requestTime, String pickupTime, String dropOffTime, String status, int fare, String route,) {
		////////////
		this.id = UUID.randomUUID();
		this.passenger = passenger;
		this.driver = driver;
		this.car = car;
		this rideType = rideType;
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.requestTime = requestTime;
		this.pickupTime = pickupTime;
		this.dropOffTime = dropOffTime;
		this.status = status;
		this.fare = fare;
		this.route = route;
	}
}
