package ca1.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;

public class ConnectionHandler implements Runnable {

    /**
     * Variables
     */
    private Server server;
    private Socket connection;
    private Logger logger;

    private OutputStream output = null;
    private PrintWriter writer;
    private BufferedReader reader;

    private boolean active = true;
    private String username = "";
    private String ipAddress = "";

    /**
     * The constructor of the ConnectionHandler class.
     *
     * @param server Takes a Server-class object.
     * @param connection Takes a Socket-class object, in this case - the
     * connection.
     * @param logger Takes a Logger-class object, so that this class can write
     * to the log.
     */
    public ConnectionHandler(Server server, Socket connection, Logger logger) {

        this.server = server;
        this.connection = connection;
        this.logger = logger;

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

                //Check for bad syntax
                if (!line.contains("#")) {

                    writer.println("BAD SYNTAX");
                    continue;

                }

                String command = line.substring(0, line.indexOf("#"));
                String message = line.substring(line.indexOf("#") + 1);

                switch (command) {

                    case "LOGIN":

                        if (server.usernameTaken(message)) {

                            writer.println("FAIL");

                        } else {

                            //Register username in class.
                            username = message;
                            ipAddress = connection.getInetAddress().toString();

                            //Register user in hashmap, and get logged in users.
                            String userList = server.addUser(this);

                            //Need to register user, and get list
                            writer.println("OK" + userList.substring(0, userList.length() - 1));

                            //Announce that the client has joined
                            server.announceNewUser(this);

                        }

                        break;

                    case "MSG":

                        //Check if it's a private message
                        if (message.contains("#")) {

                            String reciever = message.substring(0, message.indexOf("#"));
                            String privMessage = message.substring(message.indexOf("#") + 1);

                            if (reciever.equals("ALL")) {

                                server.messageEveryone(username, privMessage);

                            } else {

                                server.massageUser(username, reciever, privMessage);

                            }

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

        } catch (NullPointerException | IOException ex) {

            /**
             * Incase of a client just disconnects - then the while loop will
             * throw an Exception. The ConnectionHandler will catch it here, log
             * the incident and remove the user from the userList in server.
             */
            logger.info("Connection terminated, reason: " + ex.getMessage());
            server.removeUser(this);
            active = false;

        } finally {

            try {

                output.close();
                writer.close();
                connection.close();

            } catch (IOException ex) {

                logger.info("Error: " + ex.getMessage());

            }

        }

    }

    /**
     * Method used to send a message to the user.
     *
     * @param message String containing the message to be sent.
     */
    public void sendMessage(String message) {

        writer.println(message);

    }

    /**
     * Method for retrieving the username.
     *
     * @return A string containing the username.
     */
    public String getUsername() {

        return username;

    }

    /**
     * Method for retrieving the IP address.
     *
     * @return
     */
    public String getIpAddress() {

        return ipAddress;

    }

}
