package ch.javaee.mycv.model;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.ejb.*;

/**
 * Created by marco on 14/03/16.
 */

@Service // all the thread will call this instance
public class MongoClientProvider {

    private MongoClient mongoClient = null;

    //@Lock(LockType.READ) // full concurrent access for read
    public MongoClient getMongoClient() {
        return mongoClient;
    }

    @PostConstruct
    public void init() {

        if (System.getenv("OPENSHIFT_MONGODB_DB_URL") == null) { // Openshift URL
            mongoClient = new MongoClient();
        } else {
            mongoClient = new MongoClient(new MongoClientURI(System.getenv("OPENSHIFT_MONGODB_DB_URL")));
        }
    }
}
