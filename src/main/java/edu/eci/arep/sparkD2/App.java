package edu.eci.arep.sparkD2;

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
        sparkD.get("/test",((request, response) -> "holamundo"));
    }
}
