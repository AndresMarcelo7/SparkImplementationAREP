package edu.eci.arep.sparkD2.httpserver;

import java.util.Arrays;
import java.util.HashMap;

public class Request {
    private String method;
    private String path;
    private HashMap<String,String> headers;

    public Request() {
        headers = new HashMap<>();
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public void setHeader(String t, String d){
        this.headers.put(t,d);
    }
}
