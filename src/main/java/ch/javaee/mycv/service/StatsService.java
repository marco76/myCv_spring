package ch.javaee.mycv.service;

import ch.javaee.mycv.model.MongoClientProvider;
import ch.javaee.mycv.model.StatsWeekly;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.client.AggregateIterable;
import org.springframework.stereotype.Service;
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import static ch.javaee.mycv.model.ApplicationConstants.*;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
/**
 * Created by marco on 9/5/16.
 */
@Service
public class StatsService {

    private final static Logger LOGGER = Logger.getLogger(CvService.class.getName());

    @Autowired
    MongoClientProvider mongoClientProvider;

    public List<StatsWeekly> getLatestArticles(){

        MongoDatabase database = mongoClientProvider.getMongoClient().getDatabase(DB_CV);
       // AggregateIterable<Document> iterable = database.getCollection("visitor").aggregate( Arrays.asList(
       //         new Document("$project", new Document("year","$year:$date").append("week", new Document("$week","date"))
       //         .append("click", new Document("$sum",1)))));


        AggregateIterable<Document> iterable = database.getCollection("visitor").aggregate( Arrays.asList(
                new Document("$project", new Document("year", new Document("$year","$date")).append("month", new Document("$month","$date"))),
                new Document("$group", new Document("_id", "null").append("year", "$year").append("count", new Document("$sum", 1)))));
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                System.out.println(document.toJson());
            }
        });

        return null;
    }

}
