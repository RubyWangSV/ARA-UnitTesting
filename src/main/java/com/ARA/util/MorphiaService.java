package com.ARA.util;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.MongoCredential;

import java.util.ArrayList;
import java.util.List;

/**
 * implementation to hold our Morphia and Datastore instances
 * @author Edam
 * @version 2.0.0
 */

public class MorphiaService {

    private Morphia morphia;
    private Datastore datastore;

    public MorphiaService(){

        // connect to mlab
        Morphia morphia = new Morphia();
        ServerAddress addr = new ServerAddress("ds019886.mlab.com", 19886);
        List<MongoCredential> credentialsList = new ArrayList<MongoCredential>();
        MongoCredential credentia = MongoCredential.createCredential(
                "sumomoshinqi", "cmu_app", "thunderbird".toCharArray());
        credentialsList.add(credentia);
        MongoClient client = new MongoClient(addr, credentialsList);
        datastore = morphia.createDatastore(client, "cmu_app");
    }


    public Morphia getMorphia() {
        return morphia;
    }

    public void setMorphia(Morphia morphia) {
        this.morphia = morphia;
    }

    public Datastore getDatastore() {
        return datastore;
    }

    public void setDatastore(Datastore datastore) {
        this.datastore = datastore;
    }

}
