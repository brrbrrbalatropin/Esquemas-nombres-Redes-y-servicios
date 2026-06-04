package edu.escuelaing.arsw.ejercicio4_5_1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
public class WebServer {

    private static final int PORT = 35000;
    private static final String WEB_ROOT = "webroot";

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Servidor web escuchando en http://localhost:" + PORT);
            System.out.println("Sirviendo archivos desde: " + new File(WEB_ROOT).getAbsolutePath());
        } catch (IOException e) {
            System.err.println("No se pudo abrir el puerto " + PORT);
            System.exit(1);
        }

        while (true) {
            Socket clientSocket = serverSocket.accept();
            atenderSolicitud(clientSocket);
        }
    }

    private static void atenderSolicitud(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             OutputStream out = clientSocket.getOutputStream()) {

            String requestLine = in.readLine();
            if (requestLine == null || requestLine.isEmpty()) return;

            System.out.println("Request: " + requestLine);

            while (in.ready()) in.readLine();

            String[] parts = requestLine.split(" ");
            String path = (parts.length > 1) ? parts[1] : "/";
            if (path.equals("/")) path = "/index.html";

            File archivo = new File(WEB_ROOT + path);

            if (archivo.exists() && !archivo.isDirectory()) {
                String contentType = getContentType(path);
                byte[] contenido = new FileInputStream(archivo).readAllBytes();

                PrintWriter header = new PrintWriter(out, true);
                header.println("HTTP/1.1 200 OK");
                header.println("Content-Type: " + contentType);
                header.println("Content-Length: " + contenido.length);
                header.println();
                header.flush();
                out.write(contenido);
            } else {
                String body = "<html><body><h1>404 - Archivo no encontrado</h1><p>" + path + "</p></body></html>";
                PrintWriter writer = new PrintWriter(out, true);
                writer.println("HTTP/1.1 404 Not Found");
                writer.println("Content-Type: text/html");
                writer.println("Content-Length: " + body.length());
                writer.println();
                writer.println(body);
            }

        } catch (IOException e) {
            System.err.println("Error al atender solicitud: " + e.getMessage());
        }
    }

    private static String getContentType(String path) {
        if (path.endsWith(".html") || path.endsWith(".htm")) return "text/html";
        if (path.endsWith(".css"))  return "text/css";
        if (path.endsWith(".js"))   return "application/javascript";
        if (path.endsWith(".png"))  return "image/png";
        if (path.endsWith(".jpg") || path.endsWith(".jpeg")) return "image/jpeg";
        if (path.endsWith(".gif"))  return "image/gif";
        return "application/octet-stream";
    }
}
