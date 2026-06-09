# Java Networking Lab

A hands-on exploration of Java's networking capabilities, covering the four main communication mechanisms available in the `java.net` package: URLs, TCP Sockets, UDP Datagrams, and RMI (Remote Method Invocation).

Each package is self-contained and demonstrates a different layer of network communication, from high-level resource fetching to distributed object invocation.

---

## What's inside

### URLs
Working with Java's `URL` class to interact with internet resources.

- **UrlReader** — parses a URL and prints each of its components (protocol, host, port, path, query, etc.)
- **UrlBrowser** — a minimal browser that downloads the HTML content of any URL and saves it to `resultado.html`

### TCP Sockets
Client-server communication over TCP using `Socket` and `ServerSocket`.

- **EchoServer / EchoClient** — baseline echo server that returns whatever the client sends
- **SquareServer** — receives a number, responds with its square
- **TrigServer** — responds with sin, cos, or tan of a number; the active function can be switched at runtime with `fun:sin`, `fun:cos`, or `fun:tan`
- **WebServer** — a minimal HTTP server that serves HTML files and images from a local `webroot/` directory, handling multiple sequential requests
- **TcpClient** — generic TCP client that connects to any of the above servers by port number

### UDP Datagrams
Connectionless communication using `DatagramSocket` and `DatagramPacket`.

- **TimeServer** — responds with the current server time whenever it receives a request
- **TimeClient** — polls the server every 5 seconds and displays the time; if the server goes down, it keeps the last known time and resumes automatically when the server comes back

### RMI
Distributed object communication using Java RMI, allowing method calls across JVM instances.

- **EchoRmiServer / EchoRmiClient** — baseline RMI example: client invokes a remote method and gets a response as if it were a local call
- **ChatApp** — a peer-to-peer chat application where each instance acts as both client and server simultaneously; supports two nodes connecting by IP and port

---

## Requirements

- Java 11+
- Maven 3.8+

## Build

```bash
mvn compile
```

## Run

Each class has a `main` method and can be run directly from IntelliJ or via Maven:

```bash
mvn exec:java -Dexec.mainClass="edu.escuelaing.arsw.tcp.SquareServer"
```

For client-server pairs (TCP, UDP, RMI), start the server first, then the client. For `ChatApp`, run two instances simultaneously with mirrored port configurations.