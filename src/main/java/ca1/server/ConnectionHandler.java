package ca1.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;


public class ConnectionHandler implements Runnable {

    private Server server;
    private Socket connection;
    private boolean active = true;

    public ConnectionHandler(Server server, Socket connection) {

        this.server = server;
        this.connection = connection;

    }

    @Override
    public void run() {

        OutputStream output = null;
        
        try {
            
            output = connection.getOutputStream();
            InputStream input = connection.getInputStream();

            //Use PrintWriter instead
            PrintWriter writer = new PrintWriter(connection.getOutputStream(), true);
            // Read whatever comes in
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            while (active) {

                String line = reader.readLine();
                String[] splitString = line.split("#", 2);
                String command = line.substring(0, line.indexOf("#"));
                String message = line.substring(line.indexOf("#") + 1);
                switch (command) {

                    case "LOGIN":

                        //Need to implement username check
                        
                        //Need to register user, and get list
                        writer.println("OK#"+message+"#Anna");
                        break;

                    case "QUIT":

                        active = false;
                        break;

                    default:

                        writer.println("Wrong command!");
                        break;

                }

            }

        } catch (IOException ex) {

            System.out.println(ex.getMessage());

        } finally {

            try {

                output.close();
                connection.close();

            } catch (IOException ex) {

                ex.getMessage();

            }

        }

    }

}
