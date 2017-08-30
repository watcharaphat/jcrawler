package com.watcharaphat.app;

import com.mongodb.*;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import org.bson.Document;
import java.util.Arrays;

import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.result.DeleteResult;
import static com.mongodb.client.model.Updates.*;
import com.mongodb.client.result.UpdateResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class App
{
    public static void main( String[] args )
    {
        MongoKey mongoKey = new MongoKey();

        MongoCredential credential = MongoCredential.createCredential(mongoKey.user, mongoKey.database, mongoKey.password);
        MongoClient mongoClient = new MongoClient(new ServerAddress(mongoKey.host, mongoKey.port),
                Arrays.asList(credential));
        MongoDatabase database = mongoClient.getDatabase(mongoKey.database);

        for (String name : database.listCollectionNames()) {
            System.out.println(name);
        }
    }
}
