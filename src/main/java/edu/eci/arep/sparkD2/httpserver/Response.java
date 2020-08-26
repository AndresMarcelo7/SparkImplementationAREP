package edu.eci.arep.sparkD2.httpserver;

public class Response {
    private String mimeType="text/html";
    private String body;

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
