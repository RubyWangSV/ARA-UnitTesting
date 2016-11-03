package app.persistence;

import org.mongolink.MongoSession;
import app.domain.Repository;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class MongoRepository<T> implements Repository<T> {

    protected MongoRepository(MongoSession session) {
        this.session = session;
    }

    @Override
    public T get(Object id) {
        return session.get(id, persistentType());
    }

    @Override
    public void delete(T entity) {
        session.delete(entity);
    }

    @Override
    public void add(T entity) {
        session.save(entity);
    }

    @Override
    public List<T> all() {
        return session.getAll(persistentType());
    }

    protected final Class<T> persistentType() {
        final ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<T>) superclass.getActualTypeArguments()[0];
    }


    protected final MongoSession session;
}
