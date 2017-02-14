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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 
 */
public class Client {
    
    private static String host, userName;
    private static int port;
    private  static Socket clientSocket;
    
    static Scanner sca = new Scanner(System.in);

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }



    public static void open() throws IOException {
        clientSocket = new Socket();
        clientSocket.connect(new InetSocketAddress(host, port));
        System.out.println("Client connected to server on port " + port);
    }

    
     public static void sendMessage(String message) throws IOException {
        // Write to the server
        OutputStream output = clientSocket.getOutputStream();
        PrintWriter writer = new PrintWriter(output);
        writer.println(message);
        writer.flush();
    }
     
        public static String readMessage() throws IOException {
        // Read from the server
        InputStream input = clientSocket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String fromServer;
        while ((fromServer = reader.readLine()) == null) {

        }
        return fromServer;
    }
        
        //Terminal GUI
        
        public static void manualConnectionSetup(){
            //IP
            //Please enter the IP 
            //Needs to loop until something is correctly typed
            System.out.println("Please enter the HOST-addresse");
            host = sca.nextLine();
            System.out.println("Selected server: " + host);
            //sca.close();
            
        }
        
        public static void manualNameSetup(){
            //Please enter your name
            //Name
            //Needs to loop until something is correctly typed
            System.out.println("Please enter your name");
            userName = sca.nextLine();
            //sca.close();
 
        }
        
        //People Connected
        
        //Connected [IP]
        
        
    public static void main(String[] args) {
     manualConnectionSetup();
     manualNameSetup();
            
            
        Client client = new Client(host, 8081);
        
        try {
            
            client.open(); 
            client.sendMessage("LOGIN#"+userName);
            client.sendMessage("Hello there!!!!");
            String message = client.readMessage();
            System.out.println(message);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        
        
    }
    
    
}
