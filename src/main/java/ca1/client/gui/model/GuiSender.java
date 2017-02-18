package ca1.client.gui.model;


import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.function.Consumer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hannibal
 */
public class GuiSender implements Runnable{
    private OutputStream output;
    private String message;
    public GuiSender(OutputStream out, String msg) {
        output = out;
        message = msg;
        //System.out.println("Sender startet");
        
    }
    @Override
    public void run() {
        PrintWriter writer = new PrintWriter(output);
                writer.println(message);
                //System.out.println("Sender: " + message);
                writer.flush();
    }
}