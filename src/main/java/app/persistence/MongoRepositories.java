package app.persistence;

import org.mongolink.MongoSession;
import app.domain.*;
import app.domain.Repositories;

public class MongoRepositories extends Repositories {

    public MongoRepositories(MongoSession session) {
        this.session = session;
    }

    @Override
    protected CarRepository carsRepository() {
        return new CarMongoRepository(session);
    }

    @Override
    protected DriverRepository driversRepository() {
        return new DriverMongoRepository(session);
    }

    @Override
    protected PassengerRepository passengersRepository() {
        return new PassengerMongoRepository(session);
    }

    @Override
    protected RideRepository ridesRepository() {
        return new RideMongoRepository(session);
    }

    private MongoSession session;


}
