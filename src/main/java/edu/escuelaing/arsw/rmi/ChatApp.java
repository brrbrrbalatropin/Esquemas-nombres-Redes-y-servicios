package edu.escuelaing.arsw.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;


public class ChatApp implements ChatService {

    private String nombreUsuario;

    public ChatApp(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    @Override
    public void recibirMensaje(String mensaje) throws RemoteException {
        System.out.println(mensaje);
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Tu nombre: ");
        String nombre = scanner.nextLine().trim();

        System.out.print("Tu puerto (ej: 24000): ");
        int puertopropio = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("IP del otro (ej: 127.0.0.1): ");
        String ipOtro = scanner.nextLine().trim();

        System.out.print("Puerto del otro (ej: 24001): ");
        int puertoOtro = Integer.parseInt(scanner.nextLine().trim());

        // Publicar el servicio
        ChatApp app = new ChatApp(nombre);
        ChatService stub = (ChatService) UnicastRemoteObject.exportObject(app, 0);
        Registry registry = LocateRegistry.createRegistry(puertopropio);
        registry.rebind("chatService", stub);
        System.out.println("Esperando conexipn en puerto " + puertopropio + "...");

        // Conectarse
        ChatService otroNodo = null;
        System.out.println("Intentando conectar con " + ipOtro + ":" + puertoOtro + "...");
        while (otroNodo == null) {
            try {
                Registry registryOtro = LocateRegistry.getRegistry(ipOtro, puertoOtro);
                otroNodo = (ChatService) registryOtro.lookup("chatService");
                System.out.println("Conectado! Ya puedes chatear (\"bye\" para salir):");
            } catch (Exception e) {
                System.out.println("El otro nodo aún no está listo, reintentando...");
                Thread.sleep(2000);
            }
        }

        // Bucle de chat
        String mensaje;
        while (true) {
            mensaje = scanner.nextLine();
            if (mensaje.equalsIgnoreCase("bye")) {
                otroNodo.recibirMensaje(nombre + " se desconecto");
                break;
            }
            otroNodo.recibirMensaje(nombre + ": " + mensaje);
        }

        scanner.close();
        System.exit(0);
    }
}
