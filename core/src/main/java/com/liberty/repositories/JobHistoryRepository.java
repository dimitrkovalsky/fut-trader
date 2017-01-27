package com.liberty.repositories;

import com.liberty.config.Config;
import com.mongodb.MongoClient;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @author Dmytro_Kovalskyi.
 * @since 27.01.2017.
 */
//@Repository
public class JobHistoryRepository {
    @Autowired
    private MongoClient mongo;

    public void add(Map<String, Object> keys) {
        // TODO: make configurable / use repository ?
        // TODO: set expire TTL based on 'ts' field (ie. 7 days)
        mongo.getDatabase(Config.DATABASE_NAME).getCollection("job_history")
                .insertOne(new Document(keys));
    }

}
