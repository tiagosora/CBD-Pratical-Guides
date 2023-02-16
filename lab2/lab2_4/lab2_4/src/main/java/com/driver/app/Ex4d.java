package com.driver.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Aggregates.group;

import org.bson.Document;

public class Ex4d {

    static MongoCollection<Document> collection;
    
    public static void main(String[] args) {

        MongoClient mongoClient = new MongoClient();
        MongoDatabase database = mongoClient.getDatabase("cbd");
        collection = database.getCollection("restaurants");

        Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
        mongoLogger.setLevel(Level.SEVERE); 

        System.out.println("Numero de localidades distintas: " + countLocalidades());

        System.out.println("\nNumero de restaurantes por localidade:");
        for (Map.Entry<String, Integer> entry : countRestByLocalidade().entrySet()) {
            System.out.println(" -> " + entry.getKey() + " - " + entry.getValue());
        }

        System.out.println("\nNome de restaurantes contendo 'Park' no nome:");
        for (String nome : getRestWithNameCloserTo("Park")) {
            System.out.println(" -> " + nome);
        }

        mongoClient.close();
    }

    public static int countLocalidades() {

        int nLocalidades = 0;
        try {
            DistinctIterable<String> documents = collection.distinct("localidade",String.class);
            for (String i : documents) {
                nLocalidades++;
            }
        } catch (Exception e){ System.err.println(e); }
        return nLocalidades;
    }

    public static Map<String,Integer> countRestByLocalidade() {
        Map<String,Integer> restLocalidade = new HashMap<String,Integer>();
        try {
            AggregateIterable<Document> documents = collection.aggregate(   Arrays.asList(group("$localidade",
                                                                        Accumulators.sum("count", 1))));
            for (Document document : documents) {
                restLocalidade.put(document.get("_id").toString(),(int)document.get("count"));
            }
        } catch (Exception e){ System.err.println(e); }
        return restLocalidade;
    }

    public static List<String> getRestWithNameCloserTo(String name) {
        List<String> names = new ArrayList<String>();
        try {
            String pattern = ".*" + name + ".*";
            FindIterable<Document> documents = collection.find(regex("nome", pattern));
            for (Document document : documents) {
                names.add(document.get("nome").toString());
            }
        } catch (Exception e){ System.err.println(e); }
        return names;
    }
}