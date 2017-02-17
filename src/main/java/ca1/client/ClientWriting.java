package ca1.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that needs to allow the user to write / communicate with others on the
 * server
 */
public class ClientWriting implements Runnable {

    //Variables 'n stuff 
    private Client client;
    private static Socket clientSocket;

    private OutputStream output = null;
    private PrintWriter writer = null;
    private BufferedReader reader = null;
    private Scanner sca = new Scanner(System.in);
    private String userName, msg;

    private boolean active = false;

    /**
     * Constructor that recives the required connection information
     *
     * @param client Takes a Client object.
     * @param clientConnection Takes a Socket object and uses it to establish
     * connection with a server.
     */
    public ClientWriting(Client client, Socket clientSocket) {

        this.client = client;
        this.clientSocket = clientSocket;

    }

    /**
     * Method that allows the user to send a message through the client and to
     * the server.
     *
     * @param message Takes a String to be send to the server from the client.
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {

        writer.println(message);

    }

    /*
    Implemented Runnable Thread
     */
    @Override
    public void run() {

        try {

            output = clientSocket.getOutputStream();
            writer = new PrintWriter(output, true);

            userName();

            while (active == true) {

                msg = sca.nextLine();

                if (msg.contains("/whisper")) {

                    String[] split = msg.split(" ", 3);

                    sendMessage("MSG#" + split[1] + "#" + "[Private]" + split[2]);

                } else if (msg.equalsIgnoreCase("/quit")) {

                    active = false;

                } else if (msg.equalsIgnoreCase("/list")) {

                } else {

                    sendMessage("MSG#ALL#" + msg);

                }

            }

            System.out.println("DISCONNECTED");

        } catch (IOException ex) {
            
            Logger.getLogger(ClientWriting.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
    }

    /**
     * Method that allows the user to input a user name
     */
    public synchronized void userName() {
        try {
            
            System.out.println("Enter Username: ");
            //Awaits / Scans for the userinput
            userName = sca.nextLine();
            sendMessage("LOGIN#" + userName);
            active = true;

        } catch (IOException ex) {
            
            Logger.getLogger(ClientWriting.class.getName()).log(Level.SEVERE, null, ex);
            
        }

    }

}
