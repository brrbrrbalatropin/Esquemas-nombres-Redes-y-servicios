package edu.escuelaing.arsw.ejercicio3_2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
public class UrlBrowser {

    private static final String OUTPUT_FILE = "resultado.html";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese la URL a descargar: ");
        String input = scanner.nextLine().trim();
        scanner.close();

        try {
            URL url = new URL(input);
            descargar(url);
        } catch (MalformedURLException e) {
            System.err.println("URL mal formada: " + e.getMessage());
        }
    }

    private static void descargar(URL url) {
        System.out.println("Descargando: " + url);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
             BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE))) {

            String linea;
            int lineas = 0;
            while ((linea = reader.readLine()) != null) {
                writer.write(linea);
                writer.newLine();
                lineas++;
            }

            System.out.println("Descarga completa: " + lineas + " líneas guardadas en " + OUTPUT_FILE);

        } catch (IOException e) {
            System.err.println("Error al descargar: " + e.getMessage());
        }
    }
}
