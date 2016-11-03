package app.passenger;

import org.mongolink.domain.mapper.ContextBuilder;
import org.mongolink.*;

public class PassengerService {

    public PassengerService () {
        ContextBuilder builder = new ContextBuilder("app.passenger.mapping");
        Settings settings = Settings.defaultInstance()
                    .withDefaultUpdateStrategy(UpdateStrategies.DIFF)
                    .withDbName("cmu_appdb")
                    .withAddresses(Lists.newArrayList(new ServerAddress("ds019886.mlab.com", 19886)))
                    .withAuthentication("sumomoshinqi", "thunderbird");
        MongoSessionManager mongoSessionManager = MongoSessionManager.create(builder, settings);
    }

	public static List<Passenger> getAllPassengers = (Request request, Response response) -> {
        MongoSession session = mongoSessionManager.createSession();
        session.start();
        return session.get(persistentType());
        session.stop();
    };

    public static Passenger getPassenger = (Request request, Response response) -> {
        MongoSession session = mongoSessionManager.createSession();
        session.start();
        return session.get(request.id, persistentType());
        session.stop();
    };

    public static Passenger createPassenger = (Request request, Response response) -> {
    	Passenger passenger = new Passenger(request); // loop over request.body for parameters
    	return passenger;
    };

    public static Passenger updatePassenger = (Request request, Response response) -> {
    };
}