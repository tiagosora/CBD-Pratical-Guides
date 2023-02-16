package com.driver.app;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClient;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.Document;

import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Aggregates.limit;
import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Aggregates.project;
import static com.mongodb.client.model.Aggregates.sort;
import static com.mongodb.client.model.Aggregates.unwind;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.in;
import static com.mongodb.client.model.Filters.not;
import static com.mongodb.client.model.Filters.regex;
import static com.mongodb.client.model.Filters.size;

public class application {

    public static void main(String[] args) {
        MongoClient mongoClient = new MongoClient();
        MongoDatabase database = mongoClient.getDatabase("cbd");
        MongoCollection<Document> collection = database.getCollection("books");

        Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
        mongoLogger.setLevel(Level.SEVERE);

        // FINDS

        System.out.println("1. Listar o título, número de páginas, data de publicação e autores de todos os livros que contanham 'Software Engineering' nas categorias.\n");
        try {
            Iterable<Document> documents = collection   .find(in("categories", "Software Engineering"))
                                                        .projection(Projections.fields( Projections.exclude("_id"),
                                                                                        Projections.include("title"),
                                                                                        Projections.include("pageCount"),
                                                                                        Projections.include("publishedDate"),
                                                                                        Projections.include("authors")));
            int i = 1;
            for (Document document : documents) {
                System.out.println(i++ + " -> " + document.toJson());
            }
        } catch (Exception e) { System.err.println(e); }

        System.out.println("\n2. Listar o título, autores e data de publicação dos 10 primeiros livros, por ordem crescente de data de publicação.\n");
        try {
            Iterable<Document> documents = collection   .find()
                                                        .projection(Projections.fields( Projections.exclude("_id"),
                                                                                        Projections.include("title"),
                                                                                        Projections.include("authors"),
                                                                                        Projections.include("publishedDate")))
                                                        .sort(Sorts.ascending("publishedDate"))
                                                        .limit(10);
            int i = 1;
            for (Document document : documents) {
                System.out.println(i++ + " -> " + document.toJson());
            }
        } catch (Exception e) { System.err.println(e); }

        System.out.println("\n3. Listar o título, autores e data de publicação dos 3 primeiros livros cujo título começa por \"T\", por ordem decrescente de título.\n");
        try {
            String pattern = "^T.*";
            Iterable<Document> documents = collection   .find(regex("title", pattern))
                                                        .projection(Projections.fields( Projections.exclude("_id"),
                                                                                        Projections.include("title"),
                                                                                        Projections.include("authors"),
                                                                                        Projections.include("publishedDate")))
                                                        .sort(Sorts.descending("title"))
                                                        .limit(3);
            int i = 1;
            for (Document document : documents) {
                System.out.println(i++ + " -> " + document.toJson());
            }
        } catch (Exception e) { System.err.println(e); }



        System.out.println("\n4. Listar o título, os autores e o número de páginas de todos os livros com mais de 800 páginas, por ordem crescente de número de páginas.\n");
        try {
            Iterable<Document> documents = collection   .find(gt("pageCount", 800))
                                                        .projection(Projections.fields( Projections.exclude("_id"),
                                                                                        Projections.include("title"),
                                                                                        Projections.include("authors"),
                                                                                        Projections.include("pageCount")))
                                                        .sort(Sorts.ascending("pageCount"));
            int i = 1;
            for (Document document : documents) {
                System.out.println(i++ + " -> " + document.toJson());
            }
        } catch (Exception e) { System.err.println(e); }

        System.out.println("\n5. Listar o título, o status de todos os livros com status igual a MEAP.\n");
        try {
            Iterable<Document> documents = collection   .find(eq("status", "MEAP"))
                                                        .projection(Projections.fields( Projections.exclude("_id"),
                                                                                        Projections.include("title"),
                                                                                        Projections.include("status")));
            int i = 1;
            for (Document document : documents) {
                System.out.println(i++ + " -> " + document.toJson());
            }
        } catch (Exception e) { System.err.println(e); }

        System.out.println("\n6. Listar o título, os autores de todos os livros com 4 autores.\n");
        try {
            Iterable<Document> documents = collection   .find(and(size("authors", 4), not(in("authors", ""))))
                                                        .projection(Projections.fields( Projections.exclude("_id"),
                                                                                        Projections.include("title"),
                                                                                        Projections.include("authors")));
            int i = 1;
            for (Document document : documents) {
                System.out.println(i++ + " -> " + document.toJson());
            }
        } catch (Exception e) { System.err.println(e); }

        // AGGREGATES

        System.out.println("\n1. Contar o número de livros só de 'Software Engineering'.\n");
        try {
            AggregateIterable<Document> documents = collection.aggregate(Arrays.asList(
                    match(Filters.eq("categories",Arrays.asList("Software Engineering"))),
                    group("$categories", Accumulators.sum("books", 1))));
            int i = 1;
            for (Document document : documents) {
                System.out.println(i++ + " -> " + document.toJson());
            }
        } catch (Exception e) { System.err.println(e); }

        System.out.println("\n2. Contar o número de livros existentes por categoria, por ordem crescente de contagem.\n");
        try {
            AggregateIterable<Document> documents = collection.aggregate(Arrays.asList(
                    unwind("$categories"),
                    group("$categories", Accumulators.sum("books", 1)),
                    sort(Sorts.ascending("books"))));

            int i = 1;
            for (Document document : documents) {
                System.out.println(i++ + " -> " + document.toJson());
            }
        } catch (Exception e) { System.err.println(e); }

        System.out.println("\n3. Contar o número de livros por status, por ordem decrescente de status.\n");

        try {
            AggregateIterable<Document> documents = collection.aggregate(Arrays.asList(
                    group("$status", Accumulators.sum("status", 1)),
                    sort(Sorts.descending("status"))));
            int i = 1;
            for (Document document : documents) {
                System.out.println(i++ + " -> " + document.toJson());
            }
        } catch (Exception e) { System.err.println(e); }

        System.out.println("\n4. Listar o título, categoria e o número de páginas de todos os livros que não têm 'Web Development' como um categoria e com número " +
                            "de páginas superior a 0 e inferior a 250, por ordem crescente de número de páginas.\n");
        try {
            AggregateIterable<Document> documents = collection.aggregate(Arrays.asList(
                    project(Projections.fields(Projections.excludeId(),Projections.include("title"),Projections.include("categories"),Projections.include("pageCount"))),
                    match(Filters.and(Filters.nin("categories","Web Development"),Filters.lt("pageCount",250),Filters.gt("pageCount",0))),
                    sort(Sorts.ascending("pageCount"))));
            int i = 1;
            for (Document document : documents) {
                System.out.println(i++ + " -> " + document.toJson());
            }
        } catch (Exception e) { System.err.println(e); }

        System.out.println("\n5. Listar as 5 categorias com maior número de livros, por ordem decrescente da contagem.\n");
        try {
            AggregateIterable<Document> documents = collection.aggregate(Arrays.asList(
                    unwind("$categories"),
                    group("$categories", Accumulators.sum("books", 1)),
                    sort(Sorts.descending("books")),
                    limit(5)));
            int i = 1;
            for (Document document : documents) {
                System.out.println(i++ + " -> " + document.toJson());
            }
        } catch (Exception e) { System.err.println(e); }

        System.out.println("\n6. Contar o número de livros com status 'PUBLISH' com número de páginas superior a 300 por categoria.\n");

        try {
            AggregateIterable<Document> documents = collection.aggregate(Arrays.asList(
                    unwind("$categories"),
                    match(Filters.and(Filters.eq("status","PUBLISH"),Filters.gt("pageCount",300))),
                    group("$categories", Accumulators.sum("books", 1))));
            int i = 1;
            for (Document document : documents) {
                System.out.println(i++ + " -> " + document.toJson());
            }
        } catch (Exception e) { System.err.println(e); }
    }
}