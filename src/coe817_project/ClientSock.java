/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package coe817_project;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;



/**
 *
 *  Client socket information for single server-client connection
 * 
 * @author Barry
 */
public class ClientSock {
    
    private Socket clientSocket = null;
    private ObjectInputStream in = null;
    private ObjectOutputStream out = null;
    
    public ClientSock(Socket clientSocket){
        this.clientSocket = clientSocket;
        try {
            in = new ObjectInputStream(this.clientSocket.getInputStream());
            out = new ObjectOutputStream(this.clientSocket.getOutputStream());
                        
        } catch (IOException e){
        
            System.exit(1);
           
        }
    }
    
    public Message receiveMsg() throws IOException {
        Message newMsg = null;
        
        try {
            newMsg = (Message)in.readObject();
        } catch (ClassNotFoundException e){
            return null;
                
        }
        return newMsg;
    }
    
    public void send(Message message) throws IOException {
        out.writeObject(message);
    }
    
    public void send(ArrayList<Message> currentRoundMessages) throws IOException {
        out.writeObject(currentRoundMessages);
    }
    public void close() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
}
