package edu.escuelaing.arsw.ejercicio3_1;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlReader {

    public static void main(String[] args) {
        try {
            URL url = new URL("https://www.google.com/");

            System.out.println("URL analizada: " + url);
            System.out.println("getProtocol()  : " + url.getProtocol());
            System.out.println("getAuthority() : " + url.getAuthority());
            System.out.println("getHost()      : " + url.getHost());
            System.out.println("getPort()      : " + url.getPort());
            System.out.println("getPath()      : " + url.getPath());
            System.out.println("getQuery()     : " + url.getQuery());
            System.out.println("getFile()      : " + url.getFile());
            System.out.println("getRef()       : " + url.getRef());

        } catch (MalformedURLException e) {
            System.err.println("URL mal formada: " + e.getMessage());
        }
    }
}
