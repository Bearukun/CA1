/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca1.client;

import ca1.server.Server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that needs to allow the user to write / communicate with others on the
 * server
 *
 * @author Ceo
 */
public class ClientWritting implements Runnable {

    /*Objective
    1: Write data from the client to the server.
    2: Needs to be run as a Thread through the Main Class, alongside the Reader class.
    3: Needs to receive information regarding the server connection through the class constructor and parameters.
    4: Thread only stops when the client stops or the server disconnects.
    5: Threads purpose and function, is to allow the user to write information to others on the server.
    
    
    
     */
    private Client client;
    private static Socket clientSocket;

    private OutputStream output = null;
    private PrintWriter writer = null;
    private BufferedReader reader = null;
    private Scanner sca = new Scanner(System.in);

    private boolean active = false;

    public ClientWritting(Client client, Socket clientSocket) {
        this.client = client;
        this.clientSocket = clientSocket;
    }

    public void sendMessage(String message) throws IOException {
        // Write to the server
        OutputStream output = clientSocket.getOutputStream();
        PrintWriter writer = new PrintWriter(output);
        writer.println(message);
        writer.flush();
    }

    @Override
    public void run() {

        //while
        try {

            
                System.out.println("Enter Username: ");
                String userName = sca.nextLine();
                sendMessage("LOGIN#" + userName);
                active = true;

                while (active == true) {
                    Thread.sleep(100);
                    System.out.println("Enter Message: ");
                    String msg = sca.nextLine();
                    sendMessage("MSG#"+ userName +"#" + msg);
                    
//                    if (msg.contains("/w")) {
//                        String[] splitString = msg.split("/w", 2);
//                        String command = msg.substring(0, msg.indexOf("/w"));
//                        String receiver = msg.substring(msg.indexOf(" ") + 1, msg.indexOf(" "));
//                        
//                        
//                        sendMessage("MSG#[USER]#" + msg);
//
//                        
//                        //Enter message: /w test whateveraaaaaaaa
//                        
//                    } else {
//                        
//                    sendMessage("MSG#[ALL]#" + msg);
//
//                        
//                        
//                    }

                    if (msg.equalsIgnoreCase("QUIT")) {

                        active = false;

                    }

                }
                
                System.out.println("DISCONNECTED");
            

        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(ClientWritting.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
