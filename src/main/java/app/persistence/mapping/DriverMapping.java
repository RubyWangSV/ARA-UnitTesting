package app.persistence.mapping;

import org.mongolink.domain.mapper.AggregateMap;
import app.domain.Driver;

@SuppressWarnings("UnusedDeclaration")
public class DriverMapping extends AggregateMap<Driver> {

    @Override
    public void map() {
        id().onProperty(element().getId()).natural();
        property().onField("make");
        property().onField("model");
        property().onField("license");
        property().onField("carType");
        property().onProperty(element().getMaxPassengers());
        property().onField("validRideTypes");
    }
}
