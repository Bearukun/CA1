package ca1.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionHandler implements Runnable {

    /**
     * Variables
     */
    private Server server;
    private Socket connection;

    private OutputStream output = null;
    private PrintWriter writer;
    private BufferedReader reader;

    private boolean active = true;
    private String username = "";

    /**
     * The constructor for the ConnectionHandler.
     *
     * @param server
     * @param connection
     */
    public ConnectionHandler(Server server, Socket connection) {

        this.server = server;
        this.connection = connection;

    }

    @Override
    public void run() {

        try {

            output = connection.getOutputStream();
            InputStream input = connection.getInputStream();

            //Use PrintWriter instead
            writer = new PrintWriter(connection.getOutputStream(), true);
            // Read whatever comes in
            reader = new BufferedReader(new InputStreamReader(input));

            while (active) {
                
                String line = reader.readLine();
                
                String command = line.substring(0, line.indexOf("#"));
                String message = line.substring(line.indexOf("#") + 1);

                switch (command) {

                    case "LOGIN":

                        if (server.usernameTaken(message)) {

                            writer.println("FAIL");

                        } else {

                            //Register username in class.
                            username = message;

                            //Register user in hashmap, and get logged in users.
                            String userList = server.addUser(this);

                            //Need to register user, and get list
                            writer.println("OK" + userList.substring(0, userList.length() - 1));

                        }

                        break;

                    case "MESSAGE":

                        //Check if it's a private message
                        if (message.contains("#")) {

                            String reciever = message.substring(0, message.indexOf("#"));
                            String privMessage = message.substring(message.indexOf("#") + 1);

                            server.massageUser(username, reciever, privMessage);

                        } else {

                            server.messageEveryone(username, message);

                        }

                        break;

                    case "QUIT":

                        server.removeUser(this);
                        active = false;
                        break;

                    default:

                        writer.println("FAIL");
                        break;

                }

            }

        } catch (StringIndexOutOfBoundsException | IOException ex) {

            //Dirty fix for checking if connection is lost
            System.out.println("Connection lost or syntax error");
            server.removeUser(this);
            active = false;
            

        } finally {

            try {

                output.close();
                writer.close();
                connection.close();

            } catch (IOException ex) {

                ex.getMessage();

            }

        }

    }

    /**
     * Method used to send a message to the user.
     */
    public void sendMessage(String message) {

        writer.println(message);

    }

    /**
     * Method for retrieving username.
     *
     * @return A string containing the username.
     */
    public String getUsername() {

        return username;

    }

}
