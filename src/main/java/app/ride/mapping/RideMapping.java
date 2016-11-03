package app.ride.mapping;

import org.mongolink.domain.mapper.AggregateMap

public class RideService extends AggregateMap<Ride> {
	@Override
    public void map() {
        id().onProperty(element().getId()).natural();
        property().onField("passenger");
        property().onField("driver");
        property().onField("car");
        property().onField("rideType");
        property().onField("startPoint");
        property().onField("endPoint");
        property().onField("requestTime");
        property().onField("pickupTime");            
        property().onField("dropOffTime"); 
        property().onField("status");
        property().onField("fare");
        property().onField("route");
    }
}