package edu.eci.arep.sparkD2.urlutils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class URLScanner {

    public static void scanURL(String[] args){
        scanURL("http://ldbn.is.escuelaing.edu.co:80/AREP/Mitarea.html");
    }

    public static void scanURL(String site){
        try{
            URL siteUrl = new URL(site);
            System.out.println("URL:"+ siteUrl);
            System.out.println("Host: "+ siteUrl.getHost());
            System.out.println("PORT: "+ siteUrl.getPort());
            System.out.println("PATH: "+ siteUrl.getPath());
            System.out.println("FILE: " + siteUrl.getFile());
            System.out.println("QUERY: "+ siteUrl.getQuery());
            System.out.println("REF: "+ siteUrl.getRef());
            System.out.println("AUTHORITY: " + siteUrl.getAuthority());

            System.out.println("---------------------------------");
        }
        catch (MalformedURLException e) {
            Logger.getLogger(URLScanner.class.getName()).log(Level.SEVERE,null,e);
        }
    }
}
