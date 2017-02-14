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
 * Client for connecting a user to a (Chat) Server
 *
 * @author
 */
public class Client {

    private static String host, userName;
    private static int port;
    private static Socket clientSocket;
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
     * Method that confimes that connection has been made to a server
     *
     * @throws IOException
     */
    public static void open() throws IOException {
        clientSocket = new Socket();
        clientSocket.connect(new InetSocketAddress(host, port));
        System.out.println("Client connected to server on port " + port);
    }

    /**
     * Takes a String as a parameter and sends it as a message
     *
     * @param message String
     * @throws IOException
     */
    public static void sendMessage(String message) throws IOException {
        // Write to the server
        OutputStream output = clientSocket.getOutputStream();
        PrintWriter writer = new PrintWriter(output);
        writer.println(message);
        writer.flush();
    }

    /**
     * Method that returns a message as a String
     *
     * @return fromServer
     * @throws IOException
     */
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
    /**
     * Method that ask the user to enter the Host-address Needs to return if
     * "FAIL" is invoked due to duplicate error in connecting to server
     */
    public static void manualConnectionSetup() {
        //IP
        //Please enter the IP 
        //Needs to loop until something is correctly typed
        System.out.println("Please enter the HOST-addresse");
        host = sca.nextLine();
        System.out.println("Selected server: " + host);
        //sca.close();

    }

    /**
     * Method that ask user to enter a user name to be used in the chat Needs to
     * return if "FAIL" is invoked due to duplicate user name already online
     */
    public static void manualNameSetup() {
        //Please enter your name
        //Name
        //Needs to loop until something is correctly typed
        System.out.println("Please enter your name");
        userName = sca.nextLine();
        //sca.close();

    }

    /**
     * Method that reads messages from the server and gives the user feedback
     *
     * @param feedbackMessage
     * @throws IOException
     */
    public static void feedback(String feedbackMessage) throws IOException {
        OutputStream output = null;

        //            System.out.println("in here!");
//            output = clientSocket.getOutputStream();
//            InputStream input = clientSocket.getInputStream();
//
//            //Use PrintWriter instead
//            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
//            // Read whatever comes in
//            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        while (active) {

//                String line = reader.readLine();
//System.out.println("Line is: " + line);
            String[] splitString = feedbackMessage.split("#", 2);
            String command = feedbackMessage.substring(0, feedbackMessage.indexOf("#"));
            String message = feedbackMessage.substring(feedbackMessage.indexOf("#") + 1);

            switch (command) {

                //Login succesfull 
                //Display welcome message & online users
                case "OK":

                    //Need to implement username check
                    //Need to register user, and get list
                    System.out.println("Welcome " + feedbackMessage);

//                    Cheat/easy way of displaying people online
//                    String peopleOnline = message.replaceAll("#", " - ");
//                    System.out.println("People currently online: " + peopleOnline);
                    
                    //Display people online / People Connected
                    String[] splitStrings = message.split("#");
                    System.out.println("People currently online: ");
                    for (int i = 0; i < splitStrings.length; i++) {
                        System.out.println(splitStrings[i]);
                    }

                    active = false;
                    break;

                case "QUIT":

                    System.out.println("Goodbye!");
                    active = false;
                    break;

                case "FAIL":

                    System.out.println("Could not connect to Chat\n"
                            + " Something could be wrong with the client or server, "
                            + " or someone migt already be online with the selected User Name [" + userName + "]");

                    break;

                case "UPDATE":
                    
                    System.out.println("UPDATE ACTION");

                    break;

                case "DELETE":
                    
                    System.out.println("DELETE ACTION");
                    
                case "MSG":
                        
                    System.out.println("MESSAGE ACTION");
                    
                    break;
                    
                    
                default:

                    //writer.println("Wrong command!");
                    break;

            }

        }
        try {

            output.close();
            clientSocket.close();

        } catch (IOException ex) {

            ex.getMessage();

        }

    }

    
    //Connected [IP]
    public static void main(String[] args) {
        manualConnectionSetup();
        manualNameSetup();

        Client client = new Client(host, 8081);

        try {

            client.open();
            client.sendMessage("LOGIN#" + userName);
            client.sendMessage("Hello there!!!!");
            String message = client.readMessage();

            System.out.println(message);

            feedback(message);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
