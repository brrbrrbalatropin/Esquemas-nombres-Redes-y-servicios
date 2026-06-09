package edu.escuelaing.arsw.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
public class EchoRmiClient {

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 23000);
            EchoService echoService = (EchoService) registry.lookup("echoService");

            String respuesta = echoService.echo("Hola desde el cliente");
            System.out.println("Respuesta del servidor: " + respuesta);

        } catch (Exception e) {
            System.err.println("Error en el cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
