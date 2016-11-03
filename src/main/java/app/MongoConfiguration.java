package app;

import org.mongolink.*;
import org.mongolink.domain.mapper.ContextBuilder;

public class MongoConfiguration {

    public static void stop() {
        Singleton.INSTANCE.mongoSessionManager.close();
    }

    public static MongoSession createSession() {
        return Singleton.INSTANCE.mongoSessionManager.createSession();
    }

    private enum Singleton {

        INSTANCE;

        private Singleton() {
            ContextBuilder builder = new ContextBuilder("app.persistence.mapping");
            mongoSessionManager = MongoSessionManager.create(builder, new Properties().addSettings(Settings.defaultInstance()));
        }

        private final MongoSessionManager mongoSessionManager;
    }
}
