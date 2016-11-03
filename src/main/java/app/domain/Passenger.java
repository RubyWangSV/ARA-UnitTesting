package app.domain;
import java.util.UUID;

interface passengerValidable {
    boolean isValid();
}
public class Passenger implements passengerValidable {

    @SuppressWarnings("UnusedDeclaration")
    protected Passenger() {}

    public Passenger(String make, String model, String license, String passengerType, int maxPassengers, String validRideTypes) {
        this.id = UUID.randomUUID();
        this.make   = make;
        this.model  =  model;
        this.license    = license;
        this.passengerType    = passengerType;
        this.maxPassengers  = maxPassengers;
        this.validRideTypes = validRideTypes;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getPassengerType() {
        return passengerType;
    }

    public void setPassengerType(String passengerType) {
        this.passengerType = passengerType;
    }

    public int getMaxPassengers() {
        return maxPassengers;
    }

    public void setMaxPassengers(int maxPassengers) {
        this.maxPassengers = maxPassengers;
    }

    public String getValidRideTypes() {
        return validRideTypes;
    }

    public void setValidRideTypes(String validRideTypes) {
        this.validRideTypes = validRideTypes;
    }

    private UUID id;

    private String make;
    private String model;
    private String license;
    private String passengerType;
    private int maxPassengers;
    private String validRideTypes;  // String Array Values are ECONOMY, PREMIUM, EXECUTIVE

    @Override
    public boolean isValid() {
        return true;
    }
}
