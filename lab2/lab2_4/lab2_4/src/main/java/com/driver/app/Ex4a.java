package com.driver.app;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.MongoClient;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.conversions.Bson;
import org.bson.Document;

public class Ex4a
{
    public static void main( String[] args )
    {
        MongoClient mongoClient = new MongoClient();
        MongoDatabase database = mongoClient.getDatabase("cbd");
        MongoCollection<Document> collection = database.getCollection("restaurants");

        Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
        mongoLogger.setLevel(Level.SEVERE); 

        // a) Desenvolva um programa simples que permita testar inserção, edição e pesquisa de registos sobre a coleção.

        // INSERTING
        System.out.println("INSERTING");
        Document element = new Document("address", new  Document("building", "232")
                                                        .append("coord", Arrays.asList(40.6344210766,-8.64832160939))
                                                        .append("rua", "Aires Barbosa")
                                                        .append("zipcode", "3810"))
        .append("localidade", "Aveiro")
        .append("gastronomia", "American")
        .append("nome", "Café Convívio")
        .append("restaurant_id", "9192")
        .append("grades", new   Document()
                                .append("date","30102022")
                                .append("grade","A")
                                .append("score","101"));

        collection.insertOne(element);
        System.out.println(element);

        // UPDATING
        System.out.println("UPDATING");
        collection.updateOne(Filters.eq("nome", "Café Convívio"), Updates.set("address.zipcode","3811"));
        for (Document elementUpdated : collection.find(Filters.eq("nome", "Café Convívio"))) {
            System.out.println(elementUpdated.toJson());
        }

        // FINDING
        System.out.println("FINDING");
        Bson filter = Filters.and(Filters.eq("localidade", "Aveiro"));
        for (Document document : collection.find(filter)) {
            System.out.println(document.toJson());
        }
        mongoClient.close();
    }
}
