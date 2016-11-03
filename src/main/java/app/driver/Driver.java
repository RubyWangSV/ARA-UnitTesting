package app.driver;
import java.util.UUID;

public class Driver {
	UUID id;
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
	private String drivingLicense;
	private String licensedState;

	protected Driver() {};

	public Driver(String firstName, String lastName, String emailAddress, String password, String addressLine1, String addressLine2, String city, String state, String zip, String phoneNumber, String drivingLicense, String licensedState) {
		////////////
		this.id = UUID.randomUUID();
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
		this.drivingLicense = drivingLicense;
		this.licensedState = licensedState;				
	}
	
}
