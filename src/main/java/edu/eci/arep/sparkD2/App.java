package edu.eci.arep.sparkD2;

import com.google.gson.Gson;
import edu.eci.arep.sparkD2.data.DBConnection;
import edu.eci.arep.sparkD2.httpserver.HttpServer;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        HttpServer serv = new HttpServer();
        serv.start();

        System.out.println("Iniciando get Request");
        sparkD.get("/testGet",((request, response) -> "If you are seeing this, The test endpoint worked succesfully! :D YAY"));
        DBConnection db = new DBConnection();
        sparkD.get("/testDB", (request, response) ->  {
            StringBuilder d = new StringBuilder();
            for (String[] s: db.getNames()){
                d.append("  <tr>\n" + "    <td>").append(s[0]).append("</td>\n").append("<td> || ").append(s[1]).append("</td>  </tr>");
            }
            String o ="<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "<meta charset=\"UTF-8\">\n"
                + "<title>Title of the document</title>\n"
                + "</head>\n"
                + "<body>\n"
                + "<h1>Personas que han saludado al servidor: </h1>\n"
                + "<table>\n" +
                "  <tr>\n" +
                "    <th>Nombre</th>\n" +
                    "<th>Fecha</th>\n" +
                "  </tr>\n"
                    + d.toString()
                + "</body>\n"
                + "</html>\n";
            return o;

        }
        );
        sparkD.post("/testPost",((request, response) -> {
            response.setMimeType("text/html");
            db.insertData(request.getBody());
            return "Hello! " + request.getBody() +" Your POST request was succesfull and your name was added into the database! I'm gonna give you this random number :D " +  Math.floor(Math.random() * Math.floor(10));
        }));
    }
}
