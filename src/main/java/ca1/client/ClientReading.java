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
 *Class that needs to allow the user to see and read from the server
 * @author Ceo
 */
public class ClientReading implements Runnable{
 
    
    //Objective:
    /* 1: Class needs to read the incoming server information/Connection info and display it to the user.
        1.1: Needs a constructor, which takes in the server connection to "read from" from the main class.
    2: Class needs to start running (in/as a Thread) when executed from the main class.
    3: Thread needs to continue running, until client or server tells it to shutdown/stop.
    4: Needs to handle multiple incoming data from the server, without breaking/stopping the others threads.
    5: Reader: Needs to analyse the username and throw 'FAIL' if same username is already in use, while not breaking/stopping the Thread.
    6: Needs a simple look
    
    
    */
    
    
    private Server server;
    private Socket connection;
    

    private OutputStream output = null;
    private PrintWriter writer;
    private BufferedReader reader;

    private boolean active = true;
  
    
    
    @Override
    public void run() {
        OutputStream output = null;
        
        //Test of the out/in reader
        //FAILED! Needs 'true' connection!
//            writer.write("testing the Reading");
//            String readere = reader.readLine();
//            System.out.println(readere);
//            output = connection.getOutputStream();
//            InputStream input = connection.getInputStream();
//            writer = new PrintWriter(connection.getOutputStream(), true);
//            reader = new BufferedReader(new InputStreamReader(input));
//



//try {
//    
//    System.out.println("ReadingClient - Hello there!");
//    Thread.sleep(7000);
//    System.out.println("Reading Client - Here we go");
//    Thread.sleep(10000);
//    System.out.println("Did you finish?");

//} catch (InterruptedException ex) {
//    Logger.getLogger(ClientReading.class.getName()).log(Level.SEVERE, null, ex);
//}
//try {
//    output.close();
//} catch (IOException ex) {
//    Logger.getLogger(ClientReading.class.getName()).log(Level.SEVERE, null, ex);
//}
//    }
//    
    //Old (but still viable) skelet for reading input from the server
    //Needs to used for reading from the server
//    while (active) {
//
////                String line = reader.readLine();
////System.out.println("Line is: " + line);
//            String[] splitString = feedbackMessage.split("#", 2);
//            String command = feedbackMessage.substring(0, feedbackMessage.indexOf("#"));
//            String message = feedbackMessage.substring(feedbackMessage.indexOf("#") + 1);
//
//            switch (command) {
//
//                //Login succesfull 
//                //Display welcome message & online users
//                case "OK":
//
//                    //Need to implement username check
//                    //Need to register user, and get list
//                    System.out.println("*****************************************************");
//                    System.out.println("*               Welcome " + message + "               *");
//                    System.out.println("*****************************************************");
//
////                    Cheat/easy way of displaying people online
////                    String peopleOnline = message.replaceAll("#", " - ");
////                    System.out.println("People currently online: " + peopleOnline);
//                    //Display people online / People Connected
//                    String[] splitStrings = message.split("#");
//                    System.out.println("*****************************************************");
//                    System.out.println("People currently online: ");
//                    for (int i = 0; i < splitStrings.length; i++) {
//                        System.out.println("*   " + splitStrings[i] + "   *");
//                    }
//                    System.out.println("*****************************************************");
//
//                    active = false;
//                    break;
//
//                case "QUIT":
//
//                    System.out.println("Goodbye!");
//                    active = false;
//                    break;
//
//                case "FAIL":
//
//                    System.out.println("Could not connect to Chat\n"
//                            + " Something could be wrong with the client or server, "
//                            + " or someone migt already be online with the selected User Name [" + userName + "]");
//
//                    break;
//
//                case "UPDATE":
//
//                    System.out.println("UPDATE ACTION");
//
//                    break;
//
//                case "DELETE":
//
//                    System.out.println("DELETE ACTION");
//
//                case "MSG":
//
//                    System.out.println("MESSAGE ACTION");
//
//                    break;
//
//                default:
//
//                    //writer.println("Wrong command!");
//                    break;
//
//            }
    }
}
