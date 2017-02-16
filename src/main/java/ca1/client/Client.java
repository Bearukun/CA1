package ca1.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    //Variables
    private static ExecutorService exec = Executors.newFixedThreadPool(2);
    private Socket clientSocket;

    private String host;
    private int port;

    /**
     * Method that takes 2 parameters to be used to connect the Socket to a
     * server
     *
     * @param host Takes a String to be used as the host information
     * @param port Takes an Int to be used as the port number
     */
    public Client(String host, int port) {

        this.host = host;
        this.port = port;

    }

    /**
     * Main Thread that launches a new instance of the client.
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        Client client = new Client("localhost", 8081);
        client.startClient();

    }

    /**
     * Method that handles the setup of Sockets and executes/starts the 2
     * Executor Threads (ClientWritting & ClientReading)
     */
    public void startClient() {

        //Connection Setup
        try {

            clientSocket = new Socket();
            clientSocket.connect(new InetSocketAddress(host, port));
            System.out.println("Client connected to server on port " + port);

            //Threads handling ClientWritter and ClientReading
            exec.execute(new ClientWritting(this, clientSocket));
            exec.execute(new ClientReading(this, clientSocket));

        } catch (IOException ex) {

            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

}
