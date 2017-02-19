package ca1.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that needs to allow the user to see and read from the server
 */
public class ClientReading extends Observable implements Runnable {

    //Variables
    private Client client;
    private Socket clientConnection;
    private String message;

    private InputStream input;
    private OutputStream output = null;
    private PrintWriter writer;
    private BufferedReader reader;

    private boolean active = true;

    ArrayList<String> userList = new ArrayList();

    /**
     * Constructor to receive the connection information.
     *
     * @param client Takes a Client object.
     * @param clientConnection Takes a Socket object and uses it to establish
     * connection with a server.
     */
    public ClientReading(Client client, Socket clientConnection) {

        this.client = client;
        this.clientConnection = clientConnection;

    }

    /**
     * Implemented Runnable Thread
     */
    @Override
    public void run() {

        try {

            input = clientConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));

            while (active) {

                String msg = reader.readLine();

                String command = msg.substring(0, msg.indexOf("#"));
                message = msg.substring(msg.indexOf("#") + 1);

                switch (command) {

                    //Login succesfull 
                    //Display welcome message & online users
                    case "OK":

                        //Need to implement username check
                        //Need to register user, and get list
                        readingOK(message);
                        break;

                    case "QUIT":

                        System.out.println("Goodbye!");
                        active = false;
                        break;

                    case "FAIL":

                        System.out.println("Could not connect to Chat\n"
                                + " Something could be wrong with the client or server, "
                                + " or someone migt already be online with the selected User Name");
                        break;

                    case "UPDATE":

                        userJoinedServer(message);
                        //System.out.println("UPDATE ACTION");

                        break;

                    case "DELETE":

                        removedUser(message);
                        //System.out.println("DELETE ACTION");

                        break;

                    case "MSG":

                        chatReadAllMSG(message);

                        break;

                    default:

                        break;

                }

            }

            System.out.println("Left the ClientReading-while loop!\nRestart the client ");

        } catch (IOException ex) {

            Logger.getLogger(ClientReading.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    /**
     * Method that Welomes the user to the server and displays all currently
     * online.
     *
     * @param message Takes a String to be displayed through SOUT. The String
     * contains the username and a list possible others online on the server
     */
    public synchronized void readingOK(String message) {

        System.out.println("*****************************************************");
        System.out.println("*               Welcome                             *");
        System.out.println("*****************************************************");

        //Display people online / People Connected
        String[] splitStrings = message.split("#");
        System.out.println("*****************************************************");
        System.out.println("People online at login: ");
        for (int i = 0; i < splitStrings.length; i++) {
            System.out.println("*   " + splitStrings[i] + "   *");
            userList.add(splitStrings[i]);
        }
        System.out.println("*****************************************************");
        setChanged();
        notifyObservers("*****************************************************");

        setChanged();
        notifyObservers("*               Welcome                             *");

        setChanged();
        notifyObservers("*****************************************************");

        setChanged();
        notifyObservers("People online at login: ");

        for (int i = 0; i < splitStrings.length; i++) {
            setChanged();
            notifyObservers("*   " + splitStrings[i] + "   *");
        }

        setChanged();
        notifyObservers("*****************************************************");
    }

    /**
     * Method that displays all the messages coming from the server to the
     * client
     *
     * @param message Takes a String (message) to be split and presented as a
     * message, with info on who wrote the message and what it (the message)
     * said.
     */
    public synchronized void chatReadAllMSG(String message) {

        //Who wrote the message
        String whoWrote = message.substring(0, message.indexOf("#"));

        //What does the message say
        String theMessage = message.substring(message.indexOf("#") + 1);

        //Print writter and message
        System.out.println(whoWrote + ": " + theMessage);
        setChanged();
        notifyObservers(whoWrote + ": " + theMessage);

    }

    /**
     * Method that tells the client if a new user has joined the server.
     *
     * @param message Takes a String to display who just logged in on the
     * server.
     */
    public synchronized void userJoinedServer(String message) {

        System.out.println("* " + message + " has joined the server! *");
        setChanged();
        notifyObservers("* " + message + " has joined the server! *");
        userList.add(message);

    }

    /**
     * Method that tells the client if a user has left/disconnected from the
     * server.
     *
     * @param message Takes a String to display and show who left / disconnected
     * from the server
     */
    public synchronized void removedUser(String message) {

        System.out.println("* " + message + " has disconnected from the server *");
        setChanged();
        notifyObservers("* " + message + " has disconnected from the server *");

        for (int i = 0; i < userList.size(); i++) {

            if (userList.get(i).equals(message)) {

                userList.remove(i);

            }

        }

    }

    /**
     * enables fetching of the list of logged in users
     *
     * @return an ArrayList of type String
     */
    public ArrayList<String> getUserList() {
        return userList;
    }

    /**
     * method for retrieving the chat message transmitted
     *
     * @return a String
     */
    public String getMessage() {
        return message;
    }

}
