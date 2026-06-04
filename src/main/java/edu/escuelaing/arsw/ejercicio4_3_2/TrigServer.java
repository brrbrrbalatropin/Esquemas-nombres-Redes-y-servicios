package edu.escuelaing.arsw.ejercicio4_3_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TrigServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35002);
            System.out.println("Servidor trigonométrico escuchando en puerto 35002...");
            System.out.println("Función inicial: coseno");
        } catch (IOException e) {
            System.err.println("No se pudo abrir el puerto 35002.");
            System.exit(1);
        }

        Socket clientSocket = serverSocket.accept();
        System.out.println("Cliente conectado: " + clientSocket.getInetAddress());

        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String funcionActual = "cos";
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            if (inputLine.equalsIgnoreCase("bye")) {
                out.println("Hasta luego.");
                break;
            }

            if (inputLine.startsWith("fun:")) {
                String nuevaFuncion = inputLine.substring(4).toLowerCase();
                if (nuevaFuncion.equals("sin") || nuevaFuncion.equals("cos") || nuevaFuncion.equals("tan")) {
                    funcionActual = nuevaFuncion;
                    System.out.println("Función cambiada a: " + funcionActual);
                    out.println("OK: función cambiada a " + funcionActual);
                } else {
                    out.println("Error: función desconocida. Use sin, cos o tan.");
                }
                continue;
            }

            try {
                double numero = Double.parseDouble(inputLine);
                double resultado = calcular(funcionActual, numero);
                System.out.println(funcionActual + "(" + numero + ") = " + resultado);
                out.println(resultado);
            } catch (NumberFormatException e) {
                out.println("Error: \"" + inputLine + "\" no es un número válido.");
            }
        }

        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }

    private static double calcular(String funcion, double valor) {
        switch (funcion) {
            case "sin": return Math.sin(valor);
            case "tan": return Math.tan(valor);
            case "cos":
            default:    return Math.cos(valor);
        }
    }
}
