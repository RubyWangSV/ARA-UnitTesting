package app;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.mongolink.Settings;


import java.io.InputStream;

public class Properties {

    public Settings addSettings(Settings settings) {
        MongoClient mongoClient;
        StringBuilder mongoClientURIBuilder = new StringBuilder();
        mongoClientURIBuilder.append("mongodb://");
        if (!getDBUser().isEmpty() && !getDBPassword().isEmpty()) {
            mongoClientURIBuilder.append(getDBUser() +":" +getDBPassword() + "@");
        }
        if (!getDBHost().isEmpty()) {
            mongoClientURIBuilder.append(getDBHost());
            if (getDBPort() != 0) {
                mongoClientURIBuilder.append(":"+getDBPort());
            }
        } else {
            mongoClientURIBuilder.append("ds019886.mlab.com:19886");
        }
        System.out.println(mongoClientURIBuilder.toString());
        mongoClient = new MongoClient(new MongoClientURI((mongoClientURIBuilder.toString())));
        if (!getDBName().isEmpty()) {
            return settings.withDatabase(mongoClient.getDatabase(getDBName()));
        } else {
            return settings.withDatabase(mongoClient.getDatabase("cmu_appdb"));
        }
    }

    public String getDBHost() {
        return getProperty("db.host");
    }

    public String getDBName() {
        return getProperty("db.name");
    }

    public int getDBPort() {
        return Integer.valueOf(getProperty("db.port"));
    }

    public String getDBUser() {
        return getProperty("db.user");
    }

    public String getDBPassword() {
        return getProperty("db.password");
    }

    private String getProperty(String nom) {
        return Config.INSTANCE.properties.getProperty(nom);
    }

    private static enum Config {
        INSTANCE;

        private Config() {
            InputStream stream = getClass().getClassLoader().getResourceAsStream("conf.properties");
            properties = new java.util.Properties();
            try {
                properties.load(stream);
                stream.close();
            } catch (Exception e) {
            }
        }

        private final java.util.Properties properties;
    }

}
