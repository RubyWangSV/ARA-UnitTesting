package app.domain;
import java.util.UUID;

interface driverValidable {
    boolean isValid();
}
public class Driver implements driverValidable {

    @SuppressWarnings("UnusedDeclaration")
    protected Driver() {}

    public Driver(String make, String model, String license, String driverType, int maxPassengers, String validRideTypes) {
        this.id = UUID.randomUUID();
        this.make   = make;
        this.model  =  model;
        this.license    = license;
        this.driverType    = driverType;
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

    public String getDriverType() {
        return driverType;
    }

    public void setDriverType(String driverType) {
        this.driverType = driverType;
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
    private String driverType;
    private int maxPassengers;
    private String validRideTypes;  // String Array Values are ECONOMY, PREMIUM, EXECUTIVE

    @Override
    public boolean isValid() {
        return true;
    }
}
