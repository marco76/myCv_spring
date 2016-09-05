package ch.javaee.mycv.service;

import ch.javaee.mycv.helper.IpAddressInfoHelper;
import ch.javaee.mycv.model.Article;
import ch.javaee.mycv.model.MongoClientProvider;
import ch.javaee.mycv.model.Visitor;
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static ch.javaee.mycv.model.ApplicationConstants.*;

/**
 * Created by marco on 14/03/16.
 */
@Service
public class CvService {

    private final static Logger LOGGER = Logger.getLogger(CvService.class.getName());

    @Autowired
    MongoClientProvider mongoClientProvider;

    public List<Article> getLatestArticles(){

        MongoDatabase database = mongoClientProvider.getMongoClient().getDatabase(DB_CV);
        MongoCollection collection = database.getCollection(DB_COLL_ARTICLE);

        FindIterable<Document> iterable = collection.find().limit(10);
        List<Article> jsonObjects = new ArrayList<>();

        for (Document document : iterable){
            jsonObjects.add(new Article(document.get("title").toString(), document.get("lead").toString(), document.get("content").toString(), document.get("author").toString()));
        }

        return jsonObjects;
    }


     public String getCvByUser(String username) {

        MongoDatabase database = mongoClientProvider.getMongoClient().getDatabase(DB_CV);
        MongoCollection collection = database.getCollection(DB_COLL_CV);

        FindIterable<Document> iterable = collection.find(new Document("user", username));

        if (iterable.first() != null) {
            return iterable.first().toJson();
        }

        return null;
    }

    /**
     * Adapter to transform an Visitor DTO based on HTTP into a Mongo Document
     * @param visitor
     * @return
     */
    private Document mongoVisitAdapter(Visitor visitor){

        Document document = new Document();
        document.append("ipAddress", visitor.getIpAdress())
                .append("user", visitor.getUser())
                .append("date", visitor.getDate());

        return document;
    }

    /**
     * Store the Visitor in the DB
     * @param visitor
     */

    private void saveVisitor(Visitor visitor) {

        MongoDatabase database = mongoClientProvider.getMongoClient().getDatabase(DB_CV);
        Document visitorDB = mongoVisitAdapter(visitor);

        try {

            String geoData = IpAddressInfoHelper.readJsonFromFreeGeoIp(visitor.getIpAdress());
            DBObject dbObject = (DBObject) JSON.parse(geoData);

            visitorDB.append("geoIpData",dbObject);

        } catch (Exception e) {

            LOGGER.info("Error accessing freegeoip using : " + visitor.getIpAdress());

        }

        database.getCollection(DB_COLL_VISITOR).insertOne(visitorDB);
    }

    /**
     * Get the Google secret authorization codes to use it's credential api
     * @return
     */
    public String getGoogleOauth(){

        MongoDatabase database = mongoClientProvider.getMongoClient().getDatabase(DB_CV);
        Document result = database.getCollection(DB_APP_DATA).find(new Document("type","google_account")).first();

        return result.toJson();
    }

    /*
    The asynchronous method cannot stay in the same class of the caller,
    the server need a proxy.
    The method is asynch because is not used by the visitor
     */

    @Async
    public  void recordVisit(Visitor visitor) {

        saveVisitor(visitor);
    }
}
