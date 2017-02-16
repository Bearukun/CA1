package ca1.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Client for connecting a user to a (Chat) Server
 *
 * @author
 */
public class Client {

    
    //Objective
    /*
    1: Create PSVM that launches/starts the Chat Client 
    2: Create and establish the connection to the server (Before the Threads are run)
    2.1:Hard-code the IP. Manuel-insert of the IP is a "Nice-to", not "Need-to"
    3: With the Server Connection Running and established: 
    From the PSVM, launch instances of the 2 classes (ClientReading & ClientWritter) as Threads with ExecutorService Threading
    4: The Server connection and the 2 Threads needs to "stay alive", until either the server or the user disconnects/ends the session.
    While-loop between the establishment of the creation of the connection and the start of the Threads? What variable/Function keeps the connection alive?
   
    */
    private static ExecutorService exec = Executors.newFixedThreadPool(2);
    
    private static String host, userName, message, messageS;
    private static int port;
    private Socket clientSocket;
    private static boolean active = true;
   

    static Scanner sca = new Scanner(System.in);

    /**
     * Method that takes 2 parameters to be used to connect the Socket to a
     * server
     *
     * @param host String
     * @param port int
     */
    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }
    
 

    /**
     *Main Thread that launches a new instance of the client.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        Client client = new Client("localhost", 8081);
        client.startClient();
        
        

    }

    /**
     *Method that handles the setup of Sockets and executes/starts the 2 Executor Threads (ClientWritting & ClientReading)
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
