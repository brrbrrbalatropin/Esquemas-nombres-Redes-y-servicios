package edu.escuelaing.arsw.ejercicio5_2_1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;

public class TimeServer {

    private static final int PORT = 45000;

    public static void main(String[] args) {
        System.out.println("Servidor de hora UDP escuchando en puerto " + PORT + "...");

        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            while (true) {
                byte[] buf = new byte[256];
                DatagramPacket request = new DatagramPacket(buf, buf.length);
                socket.receive(request);

                String hora = new Date().toString();
                byte[] respuesta = hora.getBytes();

                InetAddress address = request.getAddress();
                int port = request.getPort();
                DatagramPacket response = new DatagramPacket(respuesta, respuesta.length, address, port);
                socket.send(response);

                System.out.println("Hora enviada a " + address + ":" + port + " → " + hora);
            }
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }
}
