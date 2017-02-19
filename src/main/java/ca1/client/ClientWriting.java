package ca1.client;

import ca1.gui.newGui;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Class that needs to allow the user to write / communicate with others on the
 * server
 */
public class ClientWriting extends Observable implements Runnable {

    //Variables 'n stuff 
    private Client client;
    private static Socket clientSocket;

    private OutputStream output = null;
    private PrintWriter writer = null;
    private BufferedReader reader = null;
    private Scanner sca = new Scanner(System.in);
    private String userName, msg;
    public static boolean active = false;
    public static boolean testing = false;
    private static boolean GuiOn = false;
    public static volatile boolean waitingForGuiInput = true;
    private int sleepCyclesCount = 0;

    /**
     * Constructor that recives the required connection information
     *
     * @param client Takes a Client object.
     * @param clientSocket
     */
    public ClientWriting(Client client, Socket clientSocket) {

        this.client = client;
        this.clientSocket = clientSocket;

    }

    /**
     * Empty constructor
     */
    public ClientWriting() {
    }

    /**
     * Method that allows the user to send a message through the client and to
     * the server.
     *
     * @param message Takes a String to be send to the server from the client.
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {

        writer.println(message);

    }

    /*
    Implemented Runnable Thread
     */
    @Override
    public void run() {

        try {

            output = clientSocket.getOutputStream();
            writer = new PrintWriter(output, true);

            userName();

            while (active == true) {

                if (!testing) {
                    if (GuiOn) {
                        while (waitingForGuiInput) {
                            try {
                                Thread.sleep(5);
                                //System.out.println("sleeping "+sleepCyclesCount);
                                //sleepCyclesCount++;
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                                System.out.println("sleep exception!!!!");
                            }
                        }
                    }
                    if (GuiOn) {
                        msg = newGui.input;
                    } else if (!GuiOn) {
                        msg = sca.nextLine();
                    }
                } else if (testing) {
                    msg = "HiThere";
                }

                if (msg.contains("/whisper")) {

                    String[] split = msg.split(" ", 3);

                    sendMessage("MSG#" + split[1] + "#" + "[Private]" + split[2]);
                    waitingForGuiInput = true;
                } else if (msg.equalsIgnoreCase("/quit")) {

                    active = false;

                } else if (msg.equalsIgnoreCase("/list")) {

                } else if (msg != null) {
                    //System.out.println("notnullmsg");
                    sendMessage("MSG#ALL#" + msg);
                    waitingForGuiInput = true;
                    if (testing) {
                        //tests.ChatTest.

                        active = false;
                    }
                }

            }

            System.out.println("DISCONNECTED");

        } catch (IOException ex) {

            Logger.getLogger(ClientWriting.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    /**
     * Method that allows the user to input a user name
     */
    public synchronized void userName() {
        try {
            if (!testing) {
                System.out.println("Enter Username: ");

            }
            userName = sca.nextLine();
            sendMessage("LOGIN#" + userName);
            active = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("catch!!");
            //Logger.getLogger(ClientWriting.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    /**
     * Method used for manually feeding the Scanner object a String
     *
     * @param input takes a String as input
     */
    public static void manualScanner(String input) {
        InputStream is = new ByteArrayInputStream(input.getBytes());
        System.setIn(is);
    }

    /**
     * method to toggle if GUI is being used for the chat session
     */
    public static void GuiOn() {
        GuiOn = true;
    }

    /**
     * method to check if GUI is being used for the chat session
     *
     * @return a boolean
     */
    public static boolean isGuiOn() {
        return GuiOn;
    }
}
