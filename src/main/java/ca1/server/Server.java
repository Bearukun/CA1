package ca1.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class Server {

    private static ExecutorService es = Executors.newCachedThreadPool();
    
    private HashMap<String, ConnectionHandler> userList = new HashMap();

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

    /**
     * We need to implement this later
     */
    public void shutdownServer() {

        //join on all threads
        es.shutdown();

        try {
            es.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {

            System.out.println("Could not shut down all treads!" + ex.getMessage());
        }

    }
    
    
    public String addUser(ConnectionHandler connection){
        
        //Register user to the userlist.
        userList.put(connection.getUsername(), connection);
        
        //Return list of all logged in users;
        String temp = userList.keySet().toString().replaceAll("[\\s\\[\\]]","#");
        
        //Now we need to remove ",".
        
        return temp.replaceAll(",", "");
        
        
    }
    
    public void removeUser(ConnectionHandler connection){
        
        
        
    }
    


}
