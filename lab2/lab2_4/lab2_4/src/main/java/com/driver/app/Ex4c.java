package com.driver.app;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClient;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Aggregates.group;

import org.bson.Document;
import org.bson.conversions.Bson;

public class Ex4c {

    public static void main(String[] args) {

        MongoClient mongoClient = new MongoClient();
        MongoDatabase database = mongoClient.getDatabase("cbd");
        MongoCollection<Document> collection = database.getCollection("restaurants");

        Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
        mongoLogger.setLevel(Level.SEVERE);

        // c) Selecione 5 perguntas/comandos do exercício 2.2 e reimplemente-os em Java.

        System.out.println("2. Apresente os campos restaurant_id, nome, localidade e gastronomia para todos os documentos da coleção");

        try {
            FindIterable<Document> query =  collection  .find()
                                                        .sort(Sorts.ascending("nome"))
                                                        .projection(Projections.fields( Projections.include("restaurant_id"),
                                                                                        Projections.include("nome"),
                                                                                        Projections.include("localidade"),
                                                                                        Projections.include("gastronomia")));
            for (Document document : query) {
                System.out.println(document.toJson());
            }
        } catch (Exception e){ e.printStackTrace(); }


        System.out.println("14. Liste o nome e as avaliações dos restaurantes que obtiveram uma avaliação com um grade \"A\", um score 10 na data \"2014-08-11T00: 00: 00Z\" (ISODATE).");

        try {
            FindIterable<Document> query =  collection  .find(and(  eq("grades.grade", "A"),
                                                                    eq("score", "10"),
                                                                    eq("data", "ISODate('2014-08-11T00:00:00Z')")))
                                                        .projection(Projections.fields( Projections.include("nome"),
                                                                                        Projections.include("grades")));
            for (Document document : query) {
                System.out.println(document.toJson());
            }
        } catch (Exception e){ System.err.println(e); }

        System.out.println("\n16. Liste o restaurant_id, o nome, o endereço (address) e as coordenadas geográficas (coord) dos restaurantes onde o 2º elemento da matriz de coordenadas tem um valor superior a 42 e inferior ou igual a 52.");

        try {
            FindIterable<Document> query = collection   .find(and(  gt("address.coord.1",42),
                                                                    lte("address.coord.1",52)))
                                                        .projection(Projections.fields( Projections.include("restaurant_id"),
                                                                                        Projections.include("nome"),
                                                                                        Projections.include("address"),
                                                                                        Projections.include("coord")));
            for (Document document : query) {
                System.out.println(document.toJson());
            }
        } catch (Exception e){ System.err.println(e); }

        System.out.println("\n19. Conte o total de restaurante existentes em cada localidade.");

        try {
            AggregateIterable<Document> query = collection.aggregate(Arrays.asList(group("$localidade", Accumulators.sum("count", 1))));
            for (Document document : query) {
                System.out.println(document.toJson());
            }
        } catch (Exception e){ System.err.println(e); }

        System.out.println("\n26. Apresenta os 3 primeiros restaurantes, por número de prédio (\"building\") e gastronomia e ordenados por ordem crescente de gastronomia.");

        try {
            FindIterable<Document> query = collection   .find()
                                                        .projection(Projections.fields( Projections.include("address.building"),
                                                                                        Projections.include("gastronomia")))
                                                        .sort(Sorts.ascending("gastronomia"))
                                                        .limit(3);
            for (Document document : query) {
                System.out.println(document.toJson());
            }
        } catch (Exception e){ System.err.println(e); }

        mongoClient.close();

    }
}