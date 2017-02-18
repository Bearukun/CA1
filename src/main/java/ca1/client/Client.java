package ca1.client;

import ca1.client.gui.model.GuiReader;
import ca1.client.gui.model.GuiSender;
import java.io.IOException;
import java.io.Reader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client{

    //Variables
    private static ExecutorService exec = Executors.newFixedThreadPool(2);
    private Socket clientSocket;
    private static boolean GuiOn = false;
     public Boolean connected;
    private String reciever;
    public  GuiReader reader;
    private String host;
    private int port;

    /**
     * Method that takes 2 parameters to be used to connect the Socket to a
     * server
     *
     * @param host Takes a String to be used as the host information
     * @param port Takes an Int to be used as the port number
     */
    public Client(String host, int port) {

        this.host = host;
        this.port = port;

    }
    
     public Client() {
        reciever = "ALL";
        connected  = false;
    }
    /**
     * Main Thread that launches a new instance of the client.
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        Client client = new Client("46.101.255.231", 8081);
        if(GuiOn == false){
        client.startClient();
        } 
        //46.101.255.231
        //46.101.97.122

    }

    /**
     * Method that handles the setup of Sockets and executes/starts the 2
     * Executor Threads (ClientWritting & ClientReading)
     */
    public void startClient() {

        //Connection Setup
        try {

            clientSocket = new Socket();
            clientSocket.connect(new InetSocketAddress(host, port));
            System.out.println("Client connected to server on port " + port);

            //Threads handling ClientWritter and ClientReading
            exec.execute(new ClientWriting(this, clientSocket));
            exec.execute(new ClientReading(this, clientSocket));

        } catch (IOException ex) {

            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);

        }

    }
     public void login(String username) throws IOException {
        //connects to the server with username
        clientSocket = new Socket();
        clientSocket.connect(new InetSocketAddress("46.101.255.231", 8081));
        connected = true;
        //creating thread to listen from server
        reader = new GuiReader(clientSocket.getInputStream());
        new Thread(reader).start();
        sendMessage("LOGIN#" + username);
    }

    public void sendMessage(String message) throws IOException {
        // Creates new Thread and writes to the server
        new Thread(new GuiSender(clientSocket.getOutputStream(), message)).start();
    }
    public void GuiOn(){
        GuiOn = true;
    }
    
    public Boolean getConnected() {
        return connected;
    }

    public void setConnected(Boolean connected) {
        this.connected = connected;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }
    
    public void addObserver(Observer o){
        reader.addObserver(o);
    }
}
