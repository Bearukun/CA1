package ca1.server;

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
        
        
        
    }
    
 
    
    
    
    
    
    
}
