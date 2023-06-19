package com.example.demo3.persistence;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

/**
 *  IActionDB Interface includes connectDB default method
 */
public interface IActionDB {
    static MongoClient connectDB() {
        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017/");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        return mongoClient;
    }
}
