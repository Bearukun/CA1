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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that needs to allow the user to see and read from the server
 *
 * @author Ceo
 */
public class ClientReading implements Runnable {

    //Objective:
    /* 1: Class needs to read the incoming server information/Connection info and display it to the user.
        1.1: Needs a constructor, which takes in the server connection to "read from" from the main class.
    2: Class needs to start running (in/as a Thread) when executed from the main class.
    3: Thread needs to continue running, until client or server tells it to shutdown/stop.
    4: Needs to handle multiple incoming data from the server, without breaking/stopping the others threads.
    5: Reader: Needs to analyse the username and throw 'FAIL' if same username is already in use, while not breaking/stopping the Thread.
    6: Needs a simple look
    
    
     */
    private Client client;
    private Socket clientConnection;
    private String msg;

    private OutputStream output = null;
    private PrintWriter writer;
    private BufferedReader reader;

    private boolean active = false;

    public ClientReading(Client client, Socket clientConnection) {
        this.client = client;
        this.clientConnection = clientConnection;
    }

    public String readMessage() throws IOException {
        // Read from the server
        InputStream input = clientConnection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String fromServer;
        while ((fromServer = reader.readLine()) == null) {

        }
        return fromServer;
    }

    @Override
    public void run() {

        try {

            while (active == false) {
                String msg = readMessage();
                String[] splitString = msg.split("#", 2);
                String command = msg.substring(0, msg.indexOf("#"));
                String message = msg.substring(msg.indexOf("#") + 1);

                switch (command) {

                    //Login succesfull 
                    //Display welcome message & online users
                    case "OK":

                        //Need to implement username check
                        //Need to register user, and get list
                        System.out.println("*****************************************************");
                        System.out.println("*               Welcome " + message + "               *");
                        System.out.println("*****************************************************");

//                    Cheat/easy way of displaying people online
//                    String peopleOnline = message.replaceAll("#", " - ");
//                    System.out.println("People currently online: " + peopleOnline);
                        //Display people online / People Connected
                        String[] splitStrings = message.split("#");
                        System.out.println("*****************************************************");
                        System.out.println("People currently online: ");
                        for (int i = 0; i < splitStrings.length; i++) {
                            System.out.println("*   " + splitStrings[i] + "   *");
                        }
                        System.out.println("*****************************************************");

                        active = false;
                        break;

                    case "QUIT":

                        System.out.println("Goodbye!");
                        active = true;
                        break;

                    case "FAIL":

                        System.out.println("Could not connect to Chat\n"
                                + " Something could be wrong with the client or server, "
                                + " or someone migt already be online with the selected User Name ");

                        break;

                    case "UPDATE":

                        System.out.println("UPDATE ACTION");

                        break;

                    case "DELETE":

                        System.out.println("DELETE ACTION");

                    case "MSG":
                        

                        String[] splitString1 = message.split("#");

                        String whoWrote = message.substring(0, message.indexOf("#"));

                        String theMessage = message.substring(message.indexOf("#") + 1);

                        System.out.println(whoWrote + ": " + theMessage);

                        break;

                    default:

                        //writer.println("Wrong command!");
                        break;

                }
                System.out.println(msg);
                

            }
            System.out.println("Left the while loop!");

//Old (but still viable) skelet for reading input from the server
//Needs to used for reading from the server
//    while (active) {
//
        } catch (IOException ex) {
            Logger.getLogger(ClientReading.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
