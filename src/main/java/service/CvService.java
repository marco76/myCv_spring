package service;

import ch.javaee.mycv.model.MongoClientProvider;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Created by marco on 14/03/16.
 */
@Stateless
public class CvService {

    @EJB
    MongoClientProvider mongoClientProvider;


    public static String DB_CV = "mycv";
    public static String DB_COLL_CV = "cv";
    public static String DB_COLL_VISITOR = "visitor";

    public String getCvByUser(String username) {
        MongoDatabase database = mongoClientProvider.getMongoClient().getDatabase(DB_CV);
        MongoCollection collection = database.getCollection(DB_COLL_CV);
        FindIterable<Document> iterable = collection.find(new Document("user", username));

        if (iterable.first() != null) {
            return iterable.first().toJson();
        }
        return null;
    }

    public void saveVisitor(Document visitor) {
        MongoDatabase database = mongoClientProvider.getMongoClient().getDatabase(DB_CV);
        database.getCollection(DB_COLL_VISITOR).insertOne(visitor);

    }
}
