package app.car.mapping;

import org.mongolink.domain.mapper.AggregateMap

public class CarService extends AggregateMap<Car> {
	@Override
    public void map() {
        id().onProperty(element().getId()).natural();
        property().onField("make");
        property().onField("model");
        property().onField("license");
        //....
    }
}