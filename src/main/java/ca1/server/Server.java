package ca1.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    private static ExecutorService es = Executors.newCachedThreadPool();
    
    private final String host;
    private final int port;

    public Server(String host, int port) {

        this.host = host;
        this.port = port;

    }

    public static void main(String[] args) {

        Server server = new Server("localhost", 8081);

        try {
            
            server.startServer();
            
        } catch (IOException ex) {
            
            System.out.println("Something went wrong: " + ex.getMessage());
            
        }

    }

    /**
     * Starts running the server.
     *
     * @throws IOException If network or I/O or something goes wrong.
     */
    public void startServer() throws IOException {

        // Create a new unbound socket
        ServerSocket socket = new ServerSocket();
        // Bind to a port number
        socket.bind(new InetSocketAddress(host, port));

        System.out.println("Server listening on port " + port);

        // Wait for a connection
        Socket connection;
        while ((connection = socket.accept()) != null) {

            es.execute(new ConnectionHandler(this, connection));
           
            //Move this to Connection handler
            //connection.close();

        }

    }

}
