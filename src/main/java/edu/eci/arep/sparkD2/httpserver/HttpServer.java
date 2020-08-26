package edu.eci.arep.sparkD2.httpserver;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.*;
import edu.eci.arep.sparkD2.sparkD;
import org.bson.Document;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.model.Projections;

import java.lang.reflect.Array;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class HttpServer extends Thread{
    boolean running;
    private final static String FILE_PATH = "./src/main/resources";
    private final static String TEXT_HEADER = "HTTP/1.1 200 \r\nAccess-Control-Allow-Origin: *\r\nContent-Type: text/html\r\n\r\n";
    ServerSocket serverSocket;
    Socket clientSocket;
    PrintStream out;
    BufferedReader in;
    MongoClientURI uri;
    MongoClient mongoClient;

    public HttpServer() {
        serverSocket = null;
        String requestMessageLine;
        running=true;
        uri = new MongoClientURI(
                "mongodb+srv://AREPUser:AREPUser123@arepdbserver.po3hu.gcp.mongodb.net/sample_mflix.movies?retryWrites=true&w=majority");
        mongoClient = new MongoClient(uri);

        try {
            serverSocket = new ServerSocket(36000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
    }


    public void run(){
        try {
            while (running) {
                clientSocket = null;
                try {
                    System.out.println("Listo para recibir ...");
                    clientSocket = serverSocket.accept();
                } catch (IOException e) {
                    System.err.println("Accept failed.");
                    System.exit(1);
                }
                out = new PrintStream(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String inputLine;
                boolean firstLine = false;
                Request req = new Request();
                ArrayList<String> header = new ArrayList<>();
                while ((inputLine = in.readLine()) != null) {
                    header.add(inputLine);
                    if (!in.ready()) {
                        break;
                    }
                    if (!firstLine) {
                        String[] data = inputLine.split(" ");
                        req.setPath(data[1]);
                        req.setMethod(data[0]);
                        firstLine = true;
                    } else {
                        String[] data = inputLine.split(":");
                        req.setHeader(data[0], data[1]);
                    }
                }
                handleRequest(req);
                in.close();
                clientSocket.close();
            }
            serverSocket.close();
        }catch(IOException e){
            System.out.println("azalapastrooka");
        }
    }

    private void handleRequest(Request request) throws IOException {
        Response endp = sparkD.exec(request);
        if (endp!= null){
            headerGenerator("salida."+endp.getMimeType());
            out.print(endp.getBody());
        }
        else {
            String fileName = request.getPath();
            fileName = fileName.equals("/") ? "/index.html" : fileName;
            File file = new File(FILE_PATH + fileName);
            if (file.exists()) {
                InputStream f = new FileInputStream(FILE_PATH + fileName);
                headerGenerator(fileName);
                byte[] a = new byte[4096];
                int n;
                while ((n = f.read(a)) > 0) {
                    out.write(a, 0, n);
                }
                //getDb("Drama");
                out.close();
            } else {
                out.print("HTTP/1.0 404 Not Found \r\n" + "Content-type: text/html" + "\r\n\r\n");
                out.print("<h1> 404 File not found </h1>");
                out.close();
            }
        }

        }


    private void headerGenerator(String filename){
        String mimeType="text/plain";
        if (filename.endsWith(".html") || filename.endsWith(".htm"))
            mimeType="text/html";
        if (filename.endsWith(".css"))
            mimeType="text/css";
        else if (filename.endsWith(".jpg") || filename.endsWith(".jpeg"))
            mimeType="image/jpeg";
        else if (filename.endsWith(".gif"))
            mimeType="image/gif";
        else if (filename.endsWith(".class"))
            mimeType="application/octet-stream";
        out.print("HTTP/1.0 200 OK\r\n"+
                "Content-type: "+mimeType+"\r\n\r\n");
    }

    private void getDb(String filter){
        MongoDatabase database = mongoClient.getDatabase("sample_mflix");
        MongoCollection<Document> collection =database.getCollection("movies");
        FindIterable fit = collection.find();
        ArrayList<Document> docs = new ArrayList<Document>();
        fit.into(docs);
        for (Document doc : docs) {
            if (doc.get(filter)!= null){
                ArrayList lol = (ArrayList)doc.get("genres");
                if (lol.contains("Drama")) out.print(doc.get("title"));
            }
            else{
                continue;
            }

        }

    }




}