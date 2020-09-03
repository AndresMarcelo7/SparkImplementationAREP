package edu.eci.arep.sparkD2.httpserver;

import java.util.Arrays;
import java.util.HashMap;

/**
 * The type HTTP Request.
 */
public class Request {
    private String method;
    private String path;
    private HashMap<String,String> headers;
    private String body;

    /**
     * Instantiates a new Request.
     */
    public Request() {
        this.headers = new HashMap<>();
        this.method="";
        this.path="";
        this.body="";

    }

    /**
     * Gets the request  body.
     *
     * @return the body
     */
    public String getBody() {
        return body;
    }

    /**
     * Sets the request body.
     *
     * @param body the body
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * Gets the request method (GET/POST/PUT).
     *
     * @return the method
     */
    public String getMethod() {
        return method;
    }

    /**
     * Sets method.
     *
     * @param method the method
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * Gets THE request file/endpoint path.
     *
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets path.
     *
     * @param path the path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Gets the request headers.
     *
     * @return the headers
     */
    public HashMap<String, String> getHeaders() {
        return headers;
    }

    /**
     * Get ans specific  header .
     *
     * @param key the key
     * @return the string
     */
    public String getHeader(String key){
        return headers.get(key);
    }

    /**
     * Sets all the headers.
     *
     * @param headers the headers
     */
    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    /**
     * Set a specific header.
     *
     * @param t the t
     * @param d the d
     */
    public void setHeader(String t, String d){
        this.headers.put(t,d);
    }
}
