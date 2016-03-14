package ch.javaee.mycv.model;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import javax.annotation.PostConstruct;
import javax.ejb.*;

/**
 * Created by marco on 14/03/16.
 */

@Singleton // all the thread will call this instance
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER) // maintaned by the Container and not by the Bean
public class MongoClientProvider {

    private MongoClient mongoClient = null;

    @Lock(LockType.READ) // full concurrent access for read
    public MongoClient getMongoClient(){
        return mongoClient;
    }

    @PostConstruct
    public void init() {
        if (System.getenv("OPENSHIFT_MONGODB_DB_URL") == null){ // Openshift URL
            mongoClient = new MongoClient();
        }
        else {
           mongoClient = new MongoClient(new MongoClientURI(System.getenv("OPENSHIFT_MONGODB_DB_URL")));
        };
 }

}
