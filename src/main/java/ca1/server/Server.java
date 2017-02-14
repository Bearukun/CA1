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

    private static ExecutorService es = Executors.newCachedThreadPool();

    private Logger logger = Logger.getLogger(Server.class.getName());
    private FileHandler fh;

    private HashMap<String, ConnectionHandler> userList = new HashMap();

    private final String host;
    private final int port;

    public Server(String host, int port) {

        this.host = host;
        this.port = port;

    }

    public static void main(String[] args) {

        Server server = new Server("localhost", 8081);
        server.startServer();

    }

    /**
     * Starts running the server.
     *
     * @throws IOException If network or I/O or something goes wrong.
     */
    public void startServer() {

        try {
            //Cnfigure the logger with handler and formatter
            SimpleDateFormat format = new SimpleDateFormat("M-d_HHmmss");
            fh = new FileHandler("log-" + format.format(Calendar.getInstance().getTime()) + ".log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            
            // Create a new unbound socket
            ServerSocket socket = new ServerSocket();
            // Bind to a port number
            socket.bind(new InetSocketAddress(host, port));
            
            logger.info("Server started listenin on port" + port);
            System.out.println("Server listening on port " + port);
            
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
     * We need to implement this later
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
     * Method used to add a user to the server.
     *
     * @param connection The connection object.
     * @return A string of active users separated with #.
     */
    public String addUser(ConnectionHandler connection) {

        //Register user to the userlist.
        userList.put(connection.getUsername(), connection);

        //Add connection into the log
        logger.info(connection.getUsername() + " connected to the server on ip: " + connection.getIpAddress());

        //Now we need to remove ",".
        return userList.keySet().toString().replaceAll("[\\s\\[\\]]", "#").replaceAll(",", "");

    }

    /**
     * Method to announce that a new user has joined the server.
     *
     * @param connection The object of the new user.
     */
    public void announceNewUser(ConnectionHandler connection) {

        userList.remove(connection.getUsername());

        for (ConnectionHandler user : userList.values()) {

            user.sendMessage("UPDATE#" + connection.getUsername());

        }

    }

    /**
     * Method used to check whether a username is taken or not.
     *
     * @param username The username to be checked.
     * @return True if taken, false if not.
     */
    public boolean usernameTaken(String username) {

        for (ConnectionHandler user : userList.values()) {

            if (user.getUsername().equals(username)) {

                return true;

            }

        }

        return false;

    }

    /**
     * Method used to remove a user.
     *
     * @param connection The users ConnectionHandler.
     */
    public void removeUser(ConnectionHandler connection) {

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
     * @param username
     * @param message
     */
    public void messageEveryone(String username, String message) {

        for (ConnectionHandler user : userList.values()) {

            user.sendMessage("MSG#" + username + "#" + message);

        }

    }

    /**
     * Method used to massage privately to a user.
     *
     * @param username Senders username.
     * @param targetUsername Target username.
     * @param message The message from the user.
     */
    public void massageUser(String username, String targetUsername, String message) {

        for (ConnectionHandler user : userList.values()) {

            if (user.getUsername().equals(targetUsername)) {

                user.sendMessage("MSG#" + username + "#" + message);

            }

        }

    }

}
