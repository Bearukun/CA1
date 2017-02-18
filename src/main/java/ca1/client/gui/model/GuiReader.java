
package ca1.client.gui.model;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Hannibal
 */
public class GuiReader extends Observable implements Runnable{

    private InputStream input;

    public GuiReader(InputStream in) {
        input = in;
    }


    @Override
    public void run() {
        try {
            // Read from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String fromServer;
            while (true) {
                while ((fromServer = reader.readLine()) == null) {
                    // Wait until the server says something interesting
                }
                protocolDecoder(fromServer);
            }
        } catch (IOException ex) {
        }
    }

    public void protocolDecoder(String s) {
        //System.out.println("Reader: "+s);
        switch (s.split("#")[0]) {
            case "OK":

                String rest = "You are now connected to the server";
                setChanged();
                notifyObservers(rest);
                setChanged();
                notifyObservers(s);
                break;
            case "MSG":
                String sender;
                String message;
                sender = s.split("#")[1];
                message = s.split("#")[2];
                String msg = sender + ": " + message;
                //System.out.println(sender + " " + message);
                setChanged();
                notifyObservers(msg);
                break;
            case "FAIL":
                setChanged();
                notifyObservers("Username already taken");
                break;
            case "UPDATE":
                setChanged();
                notifyObservers(s);
                break;
            case "DELETE":
                String usermessage = s.split("#")[1]+" disconnected from the server";
                setChanged();
                notifyObservers(usermessage);
                setChanged();
                notifyObservers(s);
                break;
        }
    }

}
