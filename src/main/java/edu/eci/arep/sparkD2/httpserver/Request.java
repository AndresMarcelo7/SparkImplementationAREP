package edu.eci.arep.sparkD2.httpserver;

import java.util.Arrays;
import java.util.HashMap;

public class Request {
    private String method;
    private String path;
    private HashMap<String,String> headers;
    private String body;

    public Request() {
        this.headers = new HashMap<>();
        this.method="";
        this.path="";
        this.body="";

    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
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

    public String getHeader(String key){
        return headers.get(key);
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public void setHeader(String t, String d){
        this.headers.put(t,d);
    }
}
