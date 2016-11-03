package app.domain;
import java.util.UUID;

interface rideValidable {
    boolean isValid();
}
public class Ride implements rideValidable {

    @SuppressWarnings("UnusedDeclaration")
    protected Ride() {}

    public Ride(String make, String model, String license, String rideType, int maxPassengers, String validRideTypes) {
        this.id = UUID.randomUUID();
        this.make   = make;
        this.model  =  model;
        this.license    = license;
        this.rideType    = rideType;
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

    public String getRideType() {
        return rideType;
    }

    public void setRideType(String rideType) {
        this.rideType = rideType;
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
    private String rideType;
    private int maxPassengers;
    private String validRideTypes;  // String Array Values are ECONOMY, PREMIUM, EXECUTIVE

    @Override
    public boolean isValid() {
        return true;
    }
}
