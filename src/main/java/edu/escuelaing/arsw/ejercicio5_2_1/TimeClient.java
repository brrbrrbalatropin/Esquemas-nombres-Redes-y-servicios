package edu.escuelaing.arsw.ejercicio5_2_1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

public class TimeClient {

    private static final int SERVER_PORT = 45000;
    private static final int INTERVAL_MS = 5000;
    private static final int TIMEOUT_MS = 2000;

    public static void main(String[] args) {
        System.out.println("Cliente de hora UDP iniciado. Consultando cada 5 segundos...");
        System.out.println("(Puedes apagar y prender el servidor para probar la tolerancia a fallos)");

        String ultimaHora = "Sin datos aún";

        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(TIMEOUT_MS);
            InetAddress address = InetAddress.getByName("127.0.0.1");

            while (true) {
                byte[] buf = new byte[256];
                DatagramPacket request = new DatagramPacket(buf, buf.length, address, SERVER_PORT);
                socket.send(request);

                try {
                    DatagramPacket response = new DatagramPacket(buf, buf.length);
                    socket.receive(response);
                    ultimaHora = new String(response.getData(), 0, response.getLength());
                    System.out.println("Hora del servidor: " + ultimaHora);
                } catch (SocketTimeoutException e) {
                    System.out.println("Servidor no disponible. Última hora conocida: " + ultimaHora);
                }

                Thread.sleep(INTERVAL_MS);
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error en el cliente: " + e.getMessage());
        }
    }
}
