package app.persistence;

import org.mongolink.MongoSession;
import app.domain.*;

public class PassengerMongoRepository extends MongoRepository<Passenger> implements PassengerRepository {
    public PassengerMongoRepository(MongoSession mongoSession) {
        super(mongoSession);
    }

}
