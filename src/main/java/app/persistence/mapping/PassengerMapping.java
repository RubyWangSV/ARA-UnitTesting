package app.persistence.mapping;

import org.mongolink.domain.mapper.AggregateMap;
import app.domain.Passenger;

@SuppressWarnings("UnusedDeclaration")
public class PassengerMapping extends AggregateMap<Passenger> {

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
