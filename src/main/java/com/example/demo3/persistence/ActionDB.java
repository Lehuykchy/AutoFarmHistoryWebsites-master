package com.example.demo3.persistence;

import com.example.demo3.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DBObject;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.json.JsonObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;


/**
 *  ActionDB class.
 *  This class contains functions that communicate with MongoDB
 *
 */
public class ActionDB {
    private static Relic relic;

    public static void main(String[] args) {

    }
    public final static int countRelic(String query){
        MongoClient mongoClient = IActionDB.connectDB();
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoDatabase db = mongoClient.getDatabase("history").withCodecRegistry(codecRegistry);
        MongoCollection<Relic> collection = db.getCollection("relic", Relic.class);
        List<Relic> docs = collection.find(Filters.regex("name", query, "i")).into(new ArrayList<Relic>());
        return docs.size();
    }

    public final static int countFigure(String query){
        MongoClient mongoClient = IActionDB.connectDB();
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoDatabase db = mongoClient.getDatabase("history").withCodecRegistry(codecRegistry);
        MongoCollection<Figure> collection = db.getCollection("figure", Figure.class);
        List<Figure> docs = collection.find(Filters.regex("name", query, "i")).into(new ArrayList<Figure>());
        return docs.size();
    }

    public final static int countEvent(String query){
        MongoClient mongoClient = IActionDB.connectDB();
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoDatabase db = mongoClient.getDatabase("history").withCodecRegistry(codecRegistry);
        MongoCollection<Event> collection = db.getCollection("event", Event.class);
        List<Event> docs = collection.find(Filters.regex("name", query, "i")).into(new ArrayList<Event>());
        return docs.size();
    }

    public final static int countFestival(String query){
        MongoClient mongoClient = IActionDB.connectDB();
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoDatabase db = mongoClient.getDatabase("history").withCodecRegistry(codecRegistry);
        MongoCollection<Festival> collection = db.getCollection("festival", Festival.class);
        List<Festival> docs = collection.find(Filters.regex("name", query, "i")).into(new ArrayList<Festival>());
        return docs.size();
    }

    public final static int countDynasty(String query){
        MongoClient mongoClient = IActionDB.connectDB();
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoDatabase db = mongoClient.getDatabase("history").withCodecRegistry(codecRegistry);
        MongoCollection<Dynasty> collection = db.getCollection("dynasty", Dynasty.class);
        List<Dynasty> docs = collection.find(Filters.regex("name", query, "i")).into(new ArrayList<Dynasty>());
        return docs.size();
    }

    public static List<Relic> getListRelic(String query) {
        MongoClient mongoClient = IActionDB.connectDB();
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoDatabase db = mongoClient.getDatabase("history").withCodecRegistry(codecRegistry);
        MongoCollection<Relic> collection = db.getCollection("relic", Relic.class);

        List<Relic> docs = collection.find(Filters.regex("name", query, "i")).into(new ArrayList<Relic>());
        mongoClient.close();
        return docs;
    }

    public static List<Figure> getListFigure(String query) {
        MongoClient mongoClient = IActionDB.connectDB();
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoDatabase db = mongoClient.getDatabase("history").withCodecRegistry(codecRegistry);

        MongoCollection<Figure> collection = db.getCollection("figure", Figure.class);
        List<Figure> docs = collection.find(Filters.regex("name", query, "i")).into(new ArrayList<Figure>());
        mongoClient.close();
        return docs;
    }

    public static List<Event> getListEvent(String query) {
        MongoClient mongoClient = IActionDB.connectDB();
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoDatabase db = mongoClient.getDatabase("history").withCodecRegistry(codecRegistry);

        MongoCollection<Event> collection = db.getCollection("event", Event.class);
        List<Event> docs = collection.find(Filters.regex("name", query, "i")).into(new ArrayList<Event>());
        mongoClient.close();
        return docs;
    }

    public static List<Dynasty> getListDynasty(String query) {
        MongoClient mongoClient = IActionDB.connectDB();
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoDatabase db = mongoClient.getDatabase("history").withCodecRegistry(codecRegistry);

        MongoCollection<Dynasty> collection = db.getCollection("dynasty", Dynasty.class);
        List<Dynasty> docs = collection.find(Filters.regex("name", query, "i")).into(new ArrayList<Dynasty>());
        mongoClient.close();
        return docs;
    }

    public static List<Festival> getListFestival(String query) {
        MongoClient mongoClient = IActionDB.connectDB();
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoDatabase db = mongoClient.getDatabase("history").withCodecRegistry(codecRegistry);

        MongoCollection<Festival> collection = db.getCollection("festival", Festival.class);
        List<Festival> docs = collection.find(Filters.regex("name", query, "i")).into(new ArrayList<Festival>());
        mongoClient.close();
        return docs;
    }

    public static void saveRelic(List<Relic> data) throws IllegalAccessException {
        MongoClient mongoClient = IActionDB.connectDB();
        MongoDatabase db = mongoClient.getDatabase("history");
        MongoCollection collection = db.getCollection("relic");

        for (Relic relic: data){
            if (documentExists(collection, relic.getUrl())){
                for (Field field: relic.getClass().getDeclaredFields()){
                    field.setAccessible(true);
                    if (field.get(relic) == null){
                       continue;
                    }
                    collection.updateOne(Filters.eq("url", relic.getUrl()), Updates.set(field.getName(), field.get(relic)));
                }
            }
            else {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    String json = mapper.writeValueAsString(relic);
                    Document doc = Document.parse(json);
                    collection.insertOne(doc);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void saveFigure(List<Figure> data) throws IllegalAccessException {
        MongoClient mongoClient = IActionDB.connectDB();
        MongoDatabase db = mongoClient.getDatabase("history");
        MongoCollection collection = db.getCollection("figure");

        for (Figure item: data){
            if (documentExists(collection, item.getUrl())){
                for (Field field: item.getClass().getDeclaredFields()){
                    field.setAccessible(true);
                    if (field.get(item) == null){
                        continue;
                    }
                    collection.updateOne(Filters.eq("url", item.getUrl()), Updates.set(field.getName(), field.get(item)));
                }
            }
            else {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    String json = mapper.writeValueAsString(item);
                    Document doc = Document.parse(json);
                    collection.insertOne(doc);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void saveEvent(List<Event> data) throws IllegalAccessException {
        MongoClient mongoClient = IActionDB.connectDB();
        MongoDatabase db = mongoClient.getDatabase("history");
        MongoCollection collection = db.getCollection("event");

        for (Event item: data){
            if (documentExists(collection, item.getUrl())){
                for (Field field: item.getClass().getDeclaredFields()){
                    field.setAccessible(true);
                    if (field.get(item) == null){
                        continue;
                    }
                    collection.updateOne(Filters.eq("url", item.getUrl()), Updates.set(field.getName(), field.get(item)));
                }
            }
            else {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    String json = mapper.writeValueAsString(item);
                    Document doc = Document.parse(json);
                    collection.insertOne(doc);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void saveFestival(List<Festival> data) throws IllegalAccessException {
        MongoClient mongoClient = IActionDB.connectDB();
        MongoDatabase db = mongoClient.getDatabase("history");
        MongoCollection collection = db.getCollection("festival");

        for (Festival item: data){
            if (documentExists(collection, item.getUrl())){
                for (Field field: item.getClass().getDeclaredFields()){
                    field.setAccessible(true);
                    if (field.get(item) == null){
                        continue;
                    }
                    collection.updateOne(Filters.eq("url", item.getUrl()), Updates.set(field.getName(), field.get(item)));
                }
            }
            else {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    String json = mapper.writeValueAsString(item);
                    Document doc = Document.parse(json);
                    collection.insertOne(doc);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void saveDynasty(List<Dynasty> data) throws IllegalAccessException {
        MongoClient mongoClient = IActionDB.connectDB();
        MongoDatabase db = mongoClient.getDatabase("history");
        MongoCollection collection = db.getCollection("dynasty");

        for (Dynasty item: data){
            if (documentExists(collection, item.getUrl())){
                for (Field field: item.getClass().getDeclaredFields()){
                    field.setAccessible(true);
                    if (field.get(item) == null){
                        continue;
                    }
                    collection.updateOne(Filters.eq("url", item.getUrl()), Updates.set(field.getName(), field.get(item)));
                }
            }
            else {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    String json = mapper.writeValueAsString(item);
                    Document doc = Document.parse(json);
                    collection.insertOne(doc);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static boolean documentExists(MongoCollection collection, String url) {
        FindIterable<Document> iterable = collection.find(new Document("url", url));
        return iterable.first() != null;
    }
}
