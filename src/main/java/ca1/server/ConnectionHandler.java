package ca1.server;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;


public class ConnectionHandler implements Runnable {
    
    private Server server;
    private Socket socket;

    public ConnectionHandler(Server server, Socket socket) {
        
        this.server = server;
        this.socket = socket;
        
    }

    @Override
    public void run() {
        
               OutputStream output = connection.getOutputStream();
        InputStream input = connection.getInputStream();
        //Use PrintWriter instead
        PrintWriter writer = new  PrintWriter(connection.getOutputStream(), true);

        // Read whatever comes in
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String line = reader.readLine();
        String[] splitString = line.split("#",2);
        
        String command = line.substring(0, line.indexOf("#"));
        String message = line.substring(line.indexOf("#")+1);

        switch (command) {

            case "UPPER":

                writer.println(message.toUpperCase());
                break;

            case "LOWER":

                writer.println(message.toLowerCase());
                break;

            case "REVERSE":

                String reversed = new StringBuilder(message).reverse().toString();
                writer.println(reversed.substring(0, 1).toUpperCase() + reversed.substring(1));
                break;

            case "TRANSLATE":

                if(message.equals("hund")){

                    writer.println("dog");

                }else{

                    writer.println("Sorry, I don't know that word! ;___;'");

                }

                break;

            default:

                writer.println("Wrong command!");
                break;

        }
        
    }
    
 
    
    
    
    
    
    
}
