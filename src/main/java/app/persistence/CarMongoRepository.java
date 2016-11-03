package app.persistence;

import org.mongolink.MongoSession;
import app.domain.*;

public class CarMongoRepository extends MongoRepository<Car> implements CarRepository {
    public CarMongoRepository(MongoSession mongoSession) {
        super(mongoSession);
    }

}
