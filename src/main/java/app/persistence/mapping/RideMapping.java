package app.persistence.mapping;

import org.mongolink.domain.mapper.AggregateMap;
import app.domain.Ride;

@SuppressWarnings("UnusedDeclaration")
public class RideMapping extends AggregateMap<Ride> {

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
