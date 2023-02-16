package com.driver.app;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import com.mongodb.MongoClient;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.conversions.Bson;
import org.bson.Document;

import static com.mongodb.client.model.Filters.*;

public class Ex4b {
    
    public static void main(String[] args) {

        MongoClient mongoClient = new MongoClient();
        MongoDatabase database = mongoClient.getDatabase("cbd");
        MongoCollection<Document> collection = database.getCollection("restaurants");

        Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
        mongoLogger.setLevel(Level.SEVERE);

        // Crie índices: um para localidade; outro para gastronomia; e um de texto para o nome. Use pesquisas para testar o funcionamento e o verifique o desempenho.

        System.out.println("SEM ÍNDICES");

        Bson filter = Filters.and(Filters.eq(gt("grades.score", 70)));

        long startTime = System.currentTimeMillis();
        for (Document document : collection.find(filter)) {
            System.out.println(document.toJson());
        }
        long endTime = System.currentTimeMillis();

        System.out.println("Pesquisa sem indices: " + (endTime - startTime) + " ms.");
        // Personal result : 32ms

        // Separar as águas

        System.out.println("COM ÍNDICES");

        try {
            collection.createIndex(Indexes.ascending("localidade"));
        } catch (Exception e) { System.err.println(e); }

        try {
            collection.createIndex(Indexes.ascending("gastronomia"));
        } catch (Exception e) { System.err.println(e); }

        try {
            collection.createIndex(Indexes.text(("nome")));
        } catch (Exception e) { System.err.println(e); }

        filter = Filters.and(Filters.eq(gt("grades.score", 70)));

        startTime = System.currentTimeMillis();
        for (Document document : collection.find(filter)) {
            System.out.println(document.toJson());
        }
        endTime = System.currentTimeMillis();

        System.out.println("Pesquisa com indices: " + (endTime - startTime) + " ms.");
        // Personal result : 1ms

        mongoClient.close();
    }
}