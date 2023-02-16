package com.videoshare.app;

import com.datastax.driver.core.*;

public class App {

    public static Cluster cluster = Cluster.builder().addContactPoint("172.18.0.2").build();
    public static Session session = cluster.connect("videoshare");

    public static void insert_new_user(String username, String name, String email) {
        // for now registration_date will always be dateof(now())
        String query = String.format("insert into user (username, name, email, registration_date) values ('%s', '%s', '%s', dateof(now()));", username, name, email);
        session.execute(query);
    }

    public static void update_user_byUsername(String username, String new_username, String new_name, String new_email) {
        String query = "update user set";
        Boolean signal = false;
        if (new_username != null) {
            query += " username='"+new_username+"'";
            signal = true;
        }
        if (new_name != null) {
            if( signal == false){
                query += " name='"+new_name+"'";
            } else {
                query += ", name='"+new_name+"'";
            }
            signal = true;
        }
        if (new_email != null) {
            if( signal == false){
                query += " email='"+new_email+"'";
            } else {
                query += ", email='"+new_email+"'";
            }
        }
        query += " where username='"+username+"'";
        session.execute(query);
    }

    public static void insert_new_video(String author, String name, String description, String[] tags) {
        // for now registration_date will always be dateof(now())
        String tags_str = "{'" + String.join("','", tags) + "'}";
        String query = String.format("insert into video (id, author, name, description, tags, registration_date) values (uuid(), '%s', '%s', '%s', %s, dateof(now()));", author, name, description, tags_str);
        session.execute(query);
    }

    public static void update_video_byAuthorName(String author, String name, String new_description, String[] new_tags) {
        String query = "update video set";
        Boolean signal = false;
        if (new_description != null) {
            query += " description='"+new_description+"'";
            signal = true;
        }
        if (new_tags != null) {
            String tags_str = "{'"+String.join("','",new_tags)+"'}";
            if( signal == false){
                query += " tags="+tags_str;
            } else {
                query += ", tags="+tags_str;
            }
        }
        query += " where author='"+author+"' and name='"+name+"'";
        System.out.println(query);
        session.execute(query);
    }

	public static void main(String[] args) {
		
        // Inserção

        insert_new_user("tiagosora", "Tiago Sora", "tiagosora@ua.pt");

        String[] tags = {"Video", "Legal"};
        insert_new_video("Tiago Ora", "Video Legal", "Descrição Legal", tags);


        // Ediçao

        update_user_byUsername("tiagosora", null, "Tiago Gomes Sora", null);

        String[] new_tags = {"Video", "Muito", "Legal"};
        update_video_byAuthorName("Tiago Ora","Video Legal",null, new_tags);

        // Pesquisa

        ResultSet results = session.execute("select * from user;");

        System.out.println("\n1. Vídeos no sistema:");
        System.out.printf("%16s | %20s | %32s | %s", "Username", "Name", "Email", "Registration Date");
		for (Row row : results){
            System.out.printf("\n%16s | %20s | %32s | %s", 
            row.getString("username"),
            row.getString("name"),
            row.getString("email"),
            row.getObject("registration_date").toString());
		}
        System.out.println("\n");

        // 1. Os últimos 3 comentários introduzidos para um vídeo;
        // select * from comment where video = ff3b44e0-4ea8-4286-8aea-8ea55a5b3b83 limit 3;

        results = session.execute("select * from comment where video = 562ec83c-f65b-473c-8bd8-e085af7f8bf3 limit 3;");

        System.out.println("\n1. Os últimos 3 comentários introduzidos para um vídeo");
        System.out.printf("%40s | %20s | %64s | %s", "Id", "Author", "Comment", "Registration Date");
		for (Row row : results){
            System.out.printf("\n%40s | %20s | %64s | %s", 
            row.getObject("id").toString(),
            row.getString("author"),
            row.getString("comment"),
            row.getObject("registration_date").toString());
		}
        System.out.println("\n");
        

        // 2. Lista das tags de determinado vídeo;
        // select tags from video where id = ff3b44e0-4ea8-4286-8aea-8ea55a5b3b83 allow filtering;

        results = session.execute("select tags from video where id = 17f78651-5fed-49d3-b89e-10392c80edde allow filtering;");

        System.out.println("\n2. Lista das tags de determinado vídeo");
        System.out.printf("%30s |", "Tags");
        for (Row row : results){
            System.out.printf("\n%30s |", 
            row.getObject("tags").toString());
		}
        System.out.println("\n");

        // 3. Todos os vídeos com a tag Aveiro;
        // select * from video where tags contains 'aveiro'; 

        results = session.execute("select * from video where tags contains 'aveiro';");

        System.out.println("\n3. Todos os vídeos com a tag Aveiro");
        System.out.printf("%40s | %20s | %15s | %64s | %32s | %s", "Id", "Author", "Name", "Description", "Tags", "Registration Date");
        for (Row row : results){
            System.out.printf("\n%40s | %20s | %15s | %64s | %32s | %s ", 
            row.getObject("id").toString(),
            row.getString("author"),
            row.getString("name"),
            row.getString("description"),
            row.getObject("tags").toString(),
            row.getObject("registration_date").toString());
		}
        System.out.println("\n");

        // 4. Os últimos 5 eventos de determinado vídeo realizados por um utilizador;
        // select * from video_event where video = 4ea999f6-4a9c-4cd7-a884-edcc2dfea382 limit 5 allow filtering;

        results = session.execute("select * from video_event where user = 'aslaght6' and video = 41bc339d-5451-4909-a68a-7caedc722849 limit 5 allow filtering;");

        System.out.println("\n4. Os últimos 5 eventos de determinado vídeo realizados por um utilizador");
        System.out.printf("%10s | %40s | %10s | %10s | %s", "User", "Video", "Type", "Video Time", "Registration Date");
        for (Row row : results){
            System.out.printf("\n%10s | %40s | %10s | %10s | %s", 
            row.getObject("user").toString(),
            row.getObject("video").toString(),
            row.getString("type"),
            row.getObject("video_time").toString(),
            row.getObject("registration_date").toString());
		}
        System.out.println("\n");

        session.close();
		cluster.close();
	}
}