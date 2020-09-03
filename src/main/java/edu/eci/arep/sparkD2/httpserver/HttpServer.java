package edu.eci.arep.sparkD2.httpserver;

import edu.eci.arep.sparkD2.data.DBConnection;
import edu.eci.arep.sparkD2.sparkD;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

/**
 * The Http server.
 */
public class HttpServer extends Thread {
    private boolean running;
    private final static String FILE_PATH = "./src/main/resources";
    private final static String TEXT_HEADER = "HTTP/1.1 200 \r\nAccess-Control-Allow-Origin: *\r\nContent-Type: text/html\r\n\r\n";
    private OutputStream outputStream;

    /**
     * Gets port from the environment.
     *
     * @return the port
     */
    public static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 36000;
    }

    public void run() {
        try {
            ServerSocket serverSocket = null;
            running = true;
            try {
                serverSocket = new ServerSocket(getPort());
            } catch (IOException e) {
                System.err.println("Could not listen on port: 35000.");
                System.exit(1);
            }
            while (running) {
                Socket clientSocket = null;
                try {
                    System.out.println("Listo para recibir ...");
                    //System.out.println(serverSocket.accept()); postman ¿wtf?
                    clientSocket = serverSocket.accept();
                } catch (IOException e) {
                    System.err.println("Accept failed.");
                    System.exit(1);
                }
                readRequest(clientSocket);
                clientSocket.close();
            }
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("azalapastrooka");
        }

    }

    /**
     * Read request receives the client socket and sets the request(GET/POST) Information into a REQUEST object.
     *
     * @param clientSocket the client socket
     * @throws IOException the io exception
     */
    public void readRequest(Socket clientSocket) throws IOException {
        outputStream = clientSocket.getOutputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String inputLine;
        boolean firstLine = false;
        Request req = new Request();
        ArrayList<String> header = new ArrayList<>();

        while ((inputLine = in.readLine()) != null) {
            header.add(inputLine);
            System.out.println(inputLine);
            if (!firstLine) {
                String[] data = inputLine.split(" ");
                req.setPath(data[1]);
                req.setMethod(data[0]);
                firstLine = true;
            } else if (inputLine.length() > 0) {
                String[] entry = inputLine.split(":");
                req.setHeader(entry[0], entry[1]);
            }
            if (!in.ready() || inputLine.length() == 0) {
                break;
            }

        }
        //System.out.println(req.getPath() + req.getMethod());
        //System.out.println(req.getHeaders());
        // Rellenar el body en caso de que la peticion sea de tipo POST
        /*if(req.getMethod().equals("POST")){
            StringBuilder payload = new StringBuilder();
            while(in.ready()){
                payload.append((char) in.read());
                System.out.println("PAYLOADDDDDDD-------->" + payload.toString());
            }
            req.setBody(payload.toString());
        }*/
        StringBuilder body = new StringBuilder();
        if (req.getMethod().equals("POST")) {
            int c = 0;
            int cl = Integer.parseInt(req.getHeader("Content-Length").trim());
            for (int i = 0; i < cl; i++) {
                c = in.read();
                body.append((char) c);
                System.out.println("PAYLOADDDDDDDD ----->" + body.toString());
            }
            req.setBody(body.toString());
        }


        //req.getBody().equals("GET")|| (req.getMethod().equals("POST") && !req.getBody().equals(""))
        //System.out.println("Body: " +  req.getBody().equals("") +" " + req.getMethod());
        //System.out.println(req.getBody() + "\n" + req.getMethod() + "\n" + req.getHeaders() + "\n" + req.getPath() );
        if ((req.getMethod().equals("GET")) || (req.getMethod().equals("POST") && (!req.getBody().equals(""))))
            handleRequest(req, clientSocket);
        in.close();
        outputStream.close();

    }


    /**
     * Handle request receives a request and looks if the resource requested is a static file or an endpoint
     * then, sends the response in a PrintStream.
     *
     * @param request      the request
     * @param clientSocket the client socket
     * @throws IOException the io exception
     */
    public void handleRequest(Request request, Socket clientSocket) throws IOException {
        String fileName = request.getPath();
        PrintStream out = new PrintStream(outputStream, true);
        if (fileName.equals("/")) {
            fileName = "/index.html";
            request.setPath("/index.html");
        }
        Response endp = sparkD.exec(request); //PRIMERO MIRO SI ES UN ENDPOINT FIJADO
        if (endp != null) {
            headerGenerator("salida." + endp.getMimeType().split("/")[1]);
            out.print(endp.getBody());
            out.close();
        } else if (request.getMethod().equals("GET") && request.getPath().contains(".")) {
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

            } else {
                notFound();

            }
            out.close();
        } else {
            notFound();
        }

    }


    /**
     * Generates the header for each case extension of the requested file
     *
     * @param filename the filename
     */
    public void headerGenerator(String filename) {
        PrintStream out = new PrintStream(outputStream, true);
        String mimeType = "text/plain";
        if (filename.endsWith(".html") || filename.endsWith(".htm"))
            mimeType = "text/html";
        if (filename.endsWith(".css"))
            mimeType = "text/css";
        else if (filename.endsWith(".jpg") || filename.endsWith(".jpeg"))
            mimeType = "image/jpeg";
        else if (filename.endsWith(".gif"))
            mimeType = "image/gif";
        else if (filename.endsWith(".class"))
            mimeType = "application/octet-stream";
        out.print("HTTP/1.0 200 OK\r\n" +
                "Content-type: " + mimeType + "\r\n\r\n");

    }

    /**
     * returns a 404 not found header
     */
    public void notFound() {
        PrintStream out = new PrintStream(outputStream, true);
        out.print("HTTP/1.0 404 Not Found \r\n" + "Content-type: text/html" + "\r\n\r\n");
        out.print("<h1> 404 File not found </h1>");
        out.close();

    }

}