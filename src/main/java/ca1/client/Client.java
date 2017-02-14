package ca1.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 *
 * @author 
 */
public class Client {
    
    private final String host;
    private final int port;
    private Socket clientSocket;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }



    public void open() throws IOException {
        clientSocket = new Socket();
        clientSocket.connect(new InetSocketAddress(host, port));
        System.out.println("Client connected to server on port " + port);
    }

    
     public void sendMessage(String message) throws IOException {
        // Write to the server
        OutputStream output = clientSocket.getOutputStream();
        PrintWriter writer = new PrintWriter(output);
        writer.println(message);
        writer.flush();
    }
     
        public String readMessage() throws IOException {
        // Read from the server
        InputStream input = clientSocket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String fromServer;
        while ((fromServer = reader.readLine()) == null) {

        }
        return fromServer;
    }
    public static void main(String[] args) {
        
        
        
        
    }
    
    
}
