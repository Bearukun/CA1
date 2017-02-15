package ca1.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Server {

    //Variables.
    private static ExecutorService es = Executors.newCachedThreadPool();

    private Logger logger = Logger.getLogger(Server.class.getName());
    private FileHandler fh;

    private HashMap<String, ConnectionHandler> userList = new HashMap();

    private final String host;
    private final int port;

    /**
     * The constructor of the Server-class.
     * @param host String defining on what IP the server will listen to. 
     * @param port Integer defining the port-number. 
     */
    public Server(String host, int port) {

        this.host = host;
        this.port = port;

    }

    /**
     * Main method, not much to be said. 
     * @param args 
     */
    public static void main(String[] args) {

        Server server = new Server("localhost", 8081);
        server.startServer();

    }

    /**
     * This method is used to start the server, and will configure the logger.
     */
    public void startServer() {

        try {
            //Cnfigure the logger with handler and formatter
            SimpleDateFormat format = new SimpleDateFormat("d-M_HHmmss");
            fh = new FileHandler("server-" + format.format(Calendar.getInstance().getTime()) + ".log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            // Create a new unbound socket
            ServerSocket socket = new ServerSocket();
            // Bind to a port number
            socket.bind(new InetSocketAddress(host, port));

            logger.info("Server started listenin on port" + port);

            // Wait for a connection
            Socket connection;
            while ((connection = socket.accept()) != null) {

                es.execute(new ConnectionHandler(this, connection, logger));

            }

        } catch (IOException ex) {

            logger.info("I/O Error: " + ex.getMessage());

        } catch (SecurityException ex) {

            logger.info("Security Error: " + ex.getMessage());

        }

    }

    /**
     * Method to shut down the server, now a requirement for the assignment, but
     * still a neat method to have.
     */
    public void shutdownServer() {

        //join on all threads
        es.shutdown();

        try {

            es.awaitTermination(60, TimeUnit.SECONDS);

        } catch (InterruptedException ex) {

            logger.info("Error shutting down: " + ex.getMessage());

        }

    }

    /**
     * Method used to add a user to the server. The connection-object of the
     * user is put into the HashMap with its name as the Key-value and the
     * connection object as the value.
     *
     * @param connection The connection object.
     * @return A string of active users separated with #.
     */
    public synchronized String addUser(ConnectionHandler connection) {

        //Register user to the userlist.
        userList.put(connection.getUsername(), connection);

        //Add connection into the log
        logger.info(connection.getUsername() + " connected to the server on ip: " + connection.getIpAddress());

        //Now we need to remove ",".
        return userList.keySet().toString().replaceAll("[\\s\\[\\]]", "#").replaceAll(",", "");

    }

    /**
     * Method to announce that a new user has joined the server,
     * to all clients.
     *
     * @param connection The object of the new user.
     */
    public synchronized void announceNewUser(ConnectionHandler connection) {

        for (ConnectionHandler user : userList.values()) {

            user.sendMessage("UPDATE#" + connection.getUsername());

        }

    }

    /**
     * Method used to check whether a username is taken or not. Method loops
     * through the HashMap and checks if it contains a user, with the desired
     * username.
     *
     * @param username String containing the username to be checked.
     * @return True if taken, false if not.
     */
    public synchronized boolean usernameTaken(String username) {

        for (ConnectionHandler user : userList.values()) {

            if (user.getUsername().equals(username)) {

                return true;

            }

        }

        return false;

    }

    /**
     * Method used to remove a user. This method essentially removes the user
     * from the HashMap containing active users.
     *
     * @param connection The users ConnectionHandler.
     */
    public synchronized void removeUser(ConnectionHandler connection) {

        userList.remove(connection.getUsername());

        //Register disconnect into the log
        logger.info(connection.getUsername() + " disconnected from the server from ip: " + connection.getIpAddress());

        for (ConnectionHandler user : userList.values()) {

            user.sendMessage("DELETE#" + connection.getUsername());

        }

    }

    /**
     * Method used to massage every client on the server.
     *
     * @param username String containing the name of the user that sent the msg.
     * @param message String containing the massage to be sent to everyone.
     */
    public synchronized void messageEveryone(String username, String message) {

        for (ConnectionHandler user : userList.values()) {

            user.sendMessage("MSG#" + username + "#" + message);

        }

    }

    /**
     * Method used to massage privately to a user.
     *
     * @param username String containing the senders username.
     * @param targetUsername String containing the targets username.
     * @param message String containing the message from the user.
     */
    public synchronized void massageUser(String username, String targetUsername, String message) {

        for (ConnectionHandler user : userList.values()) {

            if (user.getUsername().equals(targetUsername)) {

                user.sendMessage("MSG#" + username + "#" + message);

            }

        }

    }

}
