package app.persistence;

import org.mongolink.MongoSession;
import app.domain.*;

public class RideMongoRepository extends MongoRepository<Ride> implements RideRepository {
    public RideMongoRepository(MongoSession mongoSession) {
        super(mongoSession);
    }

}
