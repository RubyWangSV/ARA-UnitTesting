package app.driver;

import org.mongolink.domain.mapper.ContextBuilder;
import org.mongolink.*;

public class DriverService {

    public DriverService () {
        ContextBuilder builder = new ContextBuilder("app.driver.mapping");
        Settings settings = Settings.defaultInstance()
                    .withDefaultUpdateStrategy(UpdateStrategies.DIFF)
                    .withDbName("cmu_appdb")
                    .withAddresses(Lists.newArrayList(new ServerAddress("ds019886.mlab.com", 19886)))
                    .withAuthentication("sumomoshinqi", "thunderbird");
        MongoSessionManager mongoSessionManager = MongoSessionManager.create(builder, settings);
    }

	public static List<Driver> getAllDrivers = (Request request, Response response) -> {
        MongoSession session = mongoSessionManager.createSession();
        session.start();
        return session.get(persistentType());
        session.stop();
    };

    public static Driver getDriver = (Request request, Response response) -> {
        MongoSession session = mongoSessionManager.createSession();
        session.start();
        return session.get(request.id, persistentType());
        session.stop();
    };

    public static Driver createDriver = (Request request, Response response) -> {
    	Driver driver = new Driver(request); // loop over request.body for parameters
    	return driver;
    };

    public static Driver updateDriver = (Request request, Response response) -> {
    };
}