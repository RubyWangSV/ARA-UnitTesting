package app.persistence;

import org.mongolink.MongoSession;
import app.domain.*;

public class DriverMongoRepository extends MongoRepository<Driver> implements DriverRepository {
    public DriverMongoRepository(MongoSession mongoSession) {
        super(mongoSession);
    }

}
