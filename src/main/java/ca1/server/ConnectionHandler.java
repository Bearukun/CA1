package ca1.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionHandler implements Runnable {

    private Server server;
    private Socket connection;

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
            String line = reader.readLine();
            String[] splitString = line.split("#", 2);
            String command = line.substring(0, line.indexOf("#"));
            String message = line.substring(line.indexOf("#") + 1);
            switch (command) {
                
                case "LOGIN":
                    
                    writer.println("OK#Christian#Anna");
                    break;
                    
               
                    
                default:
                    
                    writer.println("Wrong command!");
                    break;
                    
            }
        } catch (IOException ex) {
            Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                output.close();
            } catch (IOException ex) {
                Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
