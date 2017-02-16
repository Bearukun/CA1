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
    
    //Start the Chat Client
    public static void main(String[] args) throws IOException {

        Client client = new Client("localhost", 8081);
        client.startClient();
        
        //OLD MANUEL CONNECTION WITH THE SERVER - Will not loop
        //ClientHandler ch = new ClientHandler();
        
//        Socket connection;
//        //While()
//        //while(!message.equalsIgnoreCase("OK#")){
//        
//       Client client = new Client(host, 8081);
//        try {
//
//            client.open();
//            client.sendMessage("LOGIN#" + userName);
//            System.out.println("*Server message*");
//            message = client.readMessage();
//            feedback(message);
//
////            if (message.equalsIgnoreCase("FAIL")) {
////                while (message != "FAIL")
////                manualConnectionSetup();
////        manualNameSetup();
////            }
////            output.close();
////            clientSocket.close();
//        } catch (IOException ex) {
//            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
//        }

    }
    public void startClient() {
        
        
        //Connection Setup
        try {
            clientSocket = new Socket();
            clientSocket.connect(new InetSocketAddress(host, port));
            System.out.println("Client connected to server on port " + port);
           
            
            
            
//            //Experimenting with the launch of the 2 class Threads
            exec.execute(new ClientWritting(this, clientSocket));
            exec.execute(new ClientReading(this, clientSocket));
            
            
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
 

//    /**
    // OLD METHOD THAT WOULD SETUP AND ESTABLISH CONNECTION WITH THE SERVER!
//     * Method that confimes that connection has been made to a server
//     *
//     * @throws IOException
//     */
//    public static void open() throws IOException {
//        clientSocket = new Socket();
//        clientSocket.connect(new InetSocketAddress(host, port));
//        System.out.println("Client connected to server on port " + port);
//    }

//    /**
    //OLD METHOD THAT WOULD SENDMESSAGES THROUGH STREAM
//     * Takes a String as a parameter and sends it as a message
//     *
//     * @param message String
//     * @throws IOException
//     */

//    /**
    //OLD METHOD THAT WOULD READMESSAGES THROUGH STREAM
//     * Method that returns a message as a String
//     *
//     * @return fromServer
//     * @throws IOException
//     */

    

    //Terminal GUI
    /**
     * OLD MANUEL SETUP OF THE CONNECTION AND USERNAME!
     * Method that ask the user to enter the Host-address Needs to return if
     * "FAIL" is invoked due to duplicate error in connecting to server
     */
//    public static void manualConnectionSetup() {
//        //IP
//        //Please enter the IP 
//        //Needs to loop until something is correctly typed
//
//        System.out.println("*****************************************************");
//        System.out.println("*            Please enter the HOST-addresse         *");
//        System.out.println("*****************************************************");
//        System.out.println("Host Addresse: ");
//        host = sca.nextLine();
//        System.out.println("Selected server: " + host);
//        System.out.println("*****************************************************");
//
//        //Please enter your name
//        //Name
//        //Needs to loop until something is correctly typed
//        System.out.println("*****************************************************");
//        System.out.println("*               Please enter your name              *");
//        userName = sca.nextLine();
//        System.out.println("*****************************************************");
//        //sca.close();
//        //sca.close();
//
//    }

    /**
     * Method that ask user to enter a user name to be used in the chat Needs to
     * return if "FAIL" is invoked due to duplicate user name already online
     */
    /**
     * Method that reads messages from the server and gives the user feedback
     *
     * @param feedbackMessage
     * @throws IOException
     */
//    public static void feedback(String feedbackMessage) throws IOException {
//        OutputStream output = null;
//
//        //            System.out.println("in here!");
////            output = clientSocket.getOutputStream();
////            InputStream input = clientSocket.getInputStream();
////
////            //Use PrintWriter instead
////            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
////            // Read whatever comes in
////            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
//        while (active) {
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
//
//        }
//        System.out.println("End of feedback");
////            output.close();
////            clientSocket.close();
//
//    }
}
