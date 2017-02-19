package tests;

import ca1.client.Client;
import ca1.client.ClientReading;
import ca1.client.ClientWriting;
import ca1.server.Server;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

public class ChatTest {
    //public static int count = 0;
    
    public ChatTest() {
    }

//    @Test
//    public void test1clientConnectionToServer(){
//        //Client client = new Client("46.101.255.231", 8081);
//        Client client = new Client("localhost", 8081);
//        client.startClient();
//        assertTrue(client.getClientSocket().isConnected());
//    }
    
    @Test
    public void test2loginAndSendMessage(){
        Client client = new Client("localhost", 8081);
        ExecutorService exec = Executors.newFixedThreadPool(2);
        Socket clientSocket;
        
        try {

            clientSocket = new Socket();
            clientSocket.connect(new InetSocketAddress("localhost", 8081));

            //Threads handling ClientWritter and ClientReading
            ClientWriting.testing = true;
            ClientWriting.active = true;
             
            ClientWriting.manualScanner("hej");
            
            ClientReading clientreading = new ClientReading(client, clientSocket);
            exec.execute(clientreading); 
            
            
            ClientWriting clientwriting = new ClientWriting(client, clientSocket);
            exec.execute(clientwriting);
            
            exec.shutdown();
            exec.awaitTermination(2,TimeUnit.SECONDS);
            
            assertTrue(clientreading.getMessage().equals("hej#HiThere"));
            
            //assertTrue(clientreading.getUserList().size()>0);
            //assertTrue(ClientWriting.active==false);
            //assertTrue(clientreading.getUserList().size()==0);
            //assertTrue(clientreading.getMessage()!=null);
        } catch (Exception ex) {

            //Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            System.out.println("exception!");
        }
    }
}