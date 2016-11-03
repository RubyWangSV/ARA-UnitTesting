package app.ride;

import org.mongolink.domain.mapper.ContextBuilder;
import org.mongolink.*;

public class RideService {

    public RideService () {
        ContextBuilder builder = new ContextBuilder("app.ride.mapping");
        Settings settings = Settings.defaultInstance()
                    .withDefaultUpdateStrategy(UpdateStrategies.DIFF)
                    .withDbName("cmu_appdb")
                    .withAddresses(Lists.newArrayList(new ServerAddress("ds019886.mlab.com", 19886)))
                    .withAuthentication("sumomoshinqi", "thunderbird");
        MongoSessionManager mongoSessionManager = MongoSessionManager.create(builder, settings);
    }

	public static List<ride> getAllRides = (Request request, Response response) -> {
        MongoSession session = mongoSessionManager.createSession();
        session.start();
        return session.get(persistentType());
        session.stop();
    };

    public static Ride getRide = (Request request, Response response) -> {
        MongoSession session = mongoSessionManager.createSession();
        session.start();
        return session.get(request.id, persistentType());
        session.stop();
    };

    public static Ride createRide = (Request request, Response response) -> {
    	Ride ride = new Ride(request); // loop over request.body for parameters
    	return ride;
    };

    public static Ride updateride = (Request request, Response response) -> {
    };
}