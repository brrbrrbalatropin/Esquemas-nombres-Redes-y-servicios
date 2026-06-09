package edu.escuelaing.arsw.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
public class EchoRmiServer implements EchoService {

    @Override
    public String echo(String mensaje) throws RemoteException {
        System.out.println("Mensaje recibido: " + mensaje);
        return "desde el servidor: " + mensaje;
    }

    public static void main(String[] args) {
        try {
            EchoRmiServer servidor = new EchoRmiServer();
            EchoService stub = (EchoService) UnicastRemoteObject.exportObject(servidor, 0);

            Registry registry = LocateRegistry.createRegistry(23000);
            registry.rebind("echoService", stub);

            System.out.println("Servidor echo RMI listo en puerto 23000...");
        } catch (Exception e) {
            System.err.println("Error en el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
