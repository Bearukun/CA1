package ca1.client.gui.control;

import java.io.IOException;
import java.util.Observer;
import ca1.client.Client;
import ca1.client.gui.model.GuiReader;

/**
 *
 * @author Hannibal
 */
public class GuiControl {
    Client cl = new Client();
    
    public boolean getConnected(){
    return cl.getConnected();
}
    public void login(String s) throws IOException{
        cl.login(s);
    }
    
    public String getReciever(){
        return cl.getReciever();
    }
    
    public void setReciever(String s){
        cl.setReciever(s);
    }
    
    public void sendMessage(String s) throws IOException{
        cl.sendMessage(s);
    }
    
    public void addObserver(Observer o){
        cl.addObserver(o);
    }
}
