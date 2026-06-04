package edu.escuelaing.arsw.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
public class EchoClient {

    public static void main(String[] args) throws IOException {
        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            echoSocket = new Socket("127.0.0.1", 35002);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Host desconocido.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("No se pudo conectar al servidor");
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;
        System.out.println("Conectado al servidor. Escribe mensajes (\"bye\" para salir):");

        while ((userInput = stdIn.readLine()) != null) {
            out.println(userInput);
            System.out.println("Respuesta del servidor: " + in.readLine());
            if (userInput.equalsIgnoreCase("bye")) break;
        }

        out.close();
        in.close();
        stdIn.close();
        echoSocket.close();
    }
}
