package edu.eci.arep.sparkD2.httpserver;
import edu.eci.arep.sparkD2.data.DBConnection;
import edu.eci.arep.sparkD2.sparkD;
import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class HttpServer extends Thread{
    private boolean running = false;
    private static String rootPath = "src/main/resources";
    private OutputStream outputStream;

    /**
     * retorna el puerto por el que va a funcionar el servidor segun la variable de entorno PORT, por defecto es 36000
     * @return el puerto por el que va a funcionar el servidor segun la variable de entorno PORT, por defecto es 36000
     */
    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 36000;
    }

    /**
     * establece la ruta en la que se leerán los archivos estáticos, la ruta base es src/main/resources
     * @param path la ruta en la que se leerán los archivos estáticos relativa a la ruta base
     */
    public static void staticFilesLocation(String path) {
        rootPath += path;
    }

    /**
     * Inicia el servidor web en un nuevo hilo,creando un socket en el puerto especificado.
     * Permite procesar varias peticiones no concurrentes, este servidor es
     * capaz de responder con archivos de texto de tipo html,css,javascript e imágenes de tipo png y jpg
     */
    @Override
    public void run() {
        try {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(getPort());
            } catch (IOException e) {
                System.err.println("Could not listen on port:." + getPort());
                System.exit(1);
            }

            boolean running = true;
            while (running) {
                try {
                    Socket clientSocket = null;
                    try {
                        System.out.println("Listo para recibir ...");
                        clientSocket = serverSocket.accept();
                    } catch (IOException e) {
                        System.err.println("Accept failed.");
                        System.exit(1);
                    }
                    processRequest(clientSocket);
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    /**
     * Encargado de procesar las peticiones enviadas por el cliente a través del socket
     * @param clientSocket el socket del cliente
     * @throws IOException si hay problemas  leyendo la peticion o generando la respuesta
     */
    private void processRequest(Socket clientSocket) throws IOException {
        outputStream=clientSocket.getOutputStream();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        String inputLine;
        Request request = new Request();
        boolean requestLineReady = false;
        while ((inputLine=in.readLine())!=null) {
            System.out.println("Recibí: " + inputLine);

            if (!requestLineReady) {
                String[] entry = inputLine.split(" ");
                request.setMethod(entry[0]);
                request.setPath(entry[1]);
                requestLineReady = true;
            } else if(inputLine.length()>0){
                String[] entry = inputLine.split(":");
                request.setHeader(entry[0], entry[1]);
            }
            if (!in.ready()||inputLine.length()==0) {
                break;
            }
        }
        if(request.getMethod().equals("POST")){
            StringBuilder payload = new StringBuilder();
            while(in.ready()){
                payload.append((char) in.read());
            }
            request.setBody(payload.toString());
        }
        if(!request.getMethod().equals("")){
            handleRequest(request, clientSocket);
        }
        in.close();
        outputStream.close();
    }

    /**
     * Escribe el contenido de un archivo estático almacenado en la ruta especificada
     * @param path la ruta donde se buscara el archivo
     * @param header el encabezado que debe tener la respuesta segun el tipo de archivo
     * @throws IOException si el archivo no se encuentra en la ruta especificada
     */
    private void getResource(String path, String header) throws IOException {
        PrintStream out=new PrintStream(outputStream);
        File file = new File(rootPath+path);
        if(file.exists()){
            InputStream inputStream= new FileInputStream(rootPath+path);
            out.print(header);
            byte[] arrbyte= new byte[4096];
            int n;
            while ((n=inputStream.read(arrbyte))>0){
                out.write(arrbyte,0,n);
            }
        }
        else errorResponse();
        out.close();
    }


    /**
     * Procesa la petición, ejecutando el endpoint necesario o retornando el archivo estático solicitado
     * @param request la petición del cliente
     * @param clientSocket el socket por el que se enviará la respuesta
     * @throws IOException si ocurre algun problema al escribir la respuesta
     */
    private void handleRequest(Request request, Socket clientSocket) throws IOException {
        Response endpointResponse=sparkD.exec(request);
        if (request.getPath().equals("/")) {
            request.setPath("/index.html");
        }
        if(endpointResponse!=null){
            String header=generateHeader(false,endpointResponse.getMimeType());
            PrintWriter out = new PrintWriter(outputStream, true);
            out.print(header);
            out.print(endpointResponse.getBody());
            out.close();
        }
        else if (request.getMethod().equals("GET") && request.getPath().contains(".")){
            String extension = request.getPath().split("\\.")[1];
            String header;
            if (extension.equals("png")||extension.equals("jpg")) {
                header = generateHeader(true, extension);
            } else {
                if(extension.equals("js")){
                    extension="javascript";
                }
                header = generateHeader(false, "text/"+extension);
            }
            getResource(request.getPath(), header);
        }
        else errorResponse();
    }

    /**
     * construye un encabezado con el código de error 404
     */
    private void errorResponse(){
        PrintWriter out = new PrintWriter(outputStream, true);
        out.print("HTTP/1.1 404 Not Found \r\n" + "Content-type: text/html"+"\r\n\r\n");
        out.print("<h1> 404 File not found </h1>");
        out.close();
    }

    /**
     * construye un encabezado de exito con el codigo 200 dada la extension del archivo y un booleano que determina si es una imagen
     * @param isImage valor booleano que determina si el archivo de respuesta es una imagen o no
     * @param extension la extension necesaria para responder
     * @return
     */
    private String generateHeader(boolean isImage, String extension) {
        String header = null;
        if (isImage) {
            header = "HTTP/1.1 200 OK \r\nContent-Type: image/" + extension + "\r\n\r\n";
        } else {
            header = "HTTP/1.1 200 OK \r\nContent-Type: "+ extension + "\r\n\r\n";
        }
        return header;
    }

}