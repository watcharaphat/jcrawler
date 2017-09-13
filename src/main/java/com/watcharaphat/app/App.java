package com.watcharaphat.app;

import com.mongodb.*;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import org.bson.Document;

import java.util.*;

import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.result.DeleteResult;
import static com.mongodb.client.model.Updates.*;
import com.mongodb.client.result.UpdateResult;

public class App
{
    private static HashSet<String> HostContainRobotsTxt = new HashSet<String>();
    public static HashSet<String> HostCheckedRobotsTxt = new HashSet<String>();
    private static MongoCredential credential;
    private static MongoClient mongoClient;
    private static MongoDatabase db;

    public static void main(String[] args ) throws Exception {
//        MongoKey mongoKey = new MongoKey();
//
//        credential = MongoCredential.createCredential(
//                mongoKey.getUser(),
//                mongoKey.getDatabase(),
//                mongoKey.getPassword()
//        );
//
//        mongoClient = new MongoClient(
//                new ServerAddress(mongoKey.getHost(), mongoKey.getPort()),
//                Arrays.asList(credential));
//
//        db = mongoClient.getDatabase(mongoKey.getDatabase());
//
//        for (String name : db.listCollectionNames()) {
//            System.out.println(name);
//        }
//
//        System.out.println("******************************");

        Controller.start();
    }
}
