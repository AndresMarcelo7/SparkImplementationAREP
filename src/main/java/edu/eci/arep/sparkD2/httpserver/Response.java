package edu.eci.arep.sparkD2.httpserver;

/**
 * The type Response.
 */
public class Response {
    private String mimeType="text/html";
    private String body;

    /**
     * Gets response's mime type.
     *
     * @return the mime type
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * Sets response's  mime type.
     *
     * @param mimeType the mime type
     */
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    /**
     * Gets response's body.
     *
     * @return the body
     */
    public String getBody() {
        return body;
    }

    /**
     * Sets response's body.
     *
     * @param body the body
     */
    public void setBody(String body) {
        this.body = body;
    }
}
