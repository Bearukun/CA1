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
 *Class that needs to allow the user to write / communicate with others on the server
 * @author Ceo
 */
public class ClientWritting implements Runnable{

    
    /*Objective
    1: Write data from the client to the server.
    2: Needs to be run as a Thread through the Main Class, alongside the Reader class.
    3: Needs to receive information regarding the server connection through the class constructor and parameters.
    4: Thread only stops when the client stops or the server disconnects.
    5: Threads purpose and function, is to allow the user to write information to others on the server.
    
    
    
    */
    private Server server;
    private Socket connection;
    

    private OutputStream output = null;
    private PrintWriter writer;
    private BufferedReader reader;

    private boolean active = true;
  
    
    @Override
    public void run() {
        
        //Test/Experiment of writting along the Reader class in the main class
//        System.out.println("Writting Client - Hello there!");
//        try {
//            Thread.sleep(5000);
//            System.out.println("Writting Client - Here we go");
//            Thread.sleep(3000);
//            System.out.println("Writting Client - Once more into the breach");
//            System.out.println("Write something!");
//            Scanner sca = new Scanner(System.in);
//            String test = sca.nextLine();
//            System.out.println(test);
//        try {
//            output = connection.getOutputStream();
//            InputStream input = connection.getInputStream();
//
//          
//            writer = new PrintWriter(connection.getOutputStream(), true);
//           
//            reader = new BufferedReader(new InputStreamReader(input));
//        } catch (IOException ex) {
//            Logger.getLogger(ClientWritting.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        } catch (InterruptedException ex) {
//            Logger.getLogger(ClientWritting.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
    
}
