package edu.eci.arep.sparkD2.data;


import com.mongodb.MongoClient;

import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Date;

/**
 * The type Db connection allows connection to a mongo DB hosted in a Cluster (ATLAS).
 */
public class DBConnection {
    /**
     * The Uri for the connection to the DB.
     */
    MongoClientURI uri;
    /**
     * The Mongo client for call the collections.
     */
    MongoClient mongoClient;

    /**
     * Instantiates a new Db connection.
     */
    public DBConnection() {
        uri = new MongoClientURI("mongodb+srv://AREPUser:AREPUser123@arepdbserver.po3hu.gcp.mongodb.net/AREPLab3?retryWrites=true&w=majority");
        mongoClient = new MongoClient(uri);
    }

    /**
     * Get all the names from the database
     *
     * @return the array list containing the db records
     */
    public ArrayList<String[]> getNames(){

        MongoDatabase database = mongoClient.getDatabase("AREPLab3");
        MongoCollection<Document> collection =database.getCollection("AREP");
        FindIterable fit = collection.find();
        ArrayList<Document> docs = new ArrayList<Document>();
        ArrayList<String[]> results = new ArrayList<>();
        fit.into(docs);
        for (Document doc : docs) {
            if (doc.get("mensaje")!= null && doc.get("fecha")!=null){
                results.add(new String[]{doc.get("mensaje").toString(), doc.get("fecha").toString()});
            }
        }
        return results;

    }

    /**
     * Insert data to the database (Only a name).
     *
     * @param message the message
     */
    public void insertData(String message){
        mongoClient = new MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase("AREPLab3");
        MongoCollection<Document> collection =database.getCollection("AREP");
        Document document=new Document();
        document.put("mensaje",message);
        document.put("fecha",getDate());
        collection.insertOne(document);
    }

    private String getDate(){
        java.util.Date fecha = new Date();
        return (fecha.toString());
    }
}
