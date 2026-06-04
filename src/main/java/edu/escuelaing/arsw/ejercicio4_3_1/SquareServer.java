package edu.escuelaing.arsw.ejercicio4_3_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
public class SquareServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35001);
            System.out.println("Servidor escuchando en puerto 35001...");
        } catch (IOException e) {
            System.err.println("No se pudo abrir el puerto 35001.");
            System.exit(1);
        }

        Socket clientSocket = serverSocket.accept();
        System.out.println("Cliente conectado: " + clientSocket.getInetAddress());

        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            if (inputLine.equalsIgnoreCase("bye")) {
                out.println("Hasta luego.");
                break;
            }
            try {
                double numero = Double.parseDouble(inputLine);
                double cuadrado = numero * numero;
                System.out.println("Recibido: " + numero + " → cuadrado: " + cuadrado);
                out.println(cuadrado);
            } catch (NumberFormatException e) {
                out.println("Error: \"" + inputLine + "\" no es valido.");
            }
        }

        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}
