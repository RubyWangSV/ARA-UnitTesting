package app.driver.mapping;

import org.mongolink.domain.mapper.AggregateMap

public class DriverService extends AggregateMap<Driver> {
	@Override
    public void map() {
        id().onProperty(element().getId()).natural();
        property().onField("firstName");
        property().onField("lastName");
        property().onField("emailAddress");
        property().onField("password");
        property().onField("addressLine1");
        property().onField("addressLine2");                                
        property().onField("city");
        property().onField("state");        
        property().onField("zip");
        property().onField("phoneNumber");                                
        property().onField("drivingLicense");
        property().onField("licensedState");        
    }
}
