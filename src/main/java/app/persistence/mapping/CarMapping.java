package app.persistence.mapping;

import org.mongolink.domain.mapper.AggregateMap;
import app.domain.Car;

@SuppressWarnings("UnusedDeclaration")
public class CarMapping extends AggregateMap<Car> {

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
