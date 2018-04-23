/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package coe817_project;

import java.util.ArrayList;
import java.io.EOFException;
import java.io.IOException;

/**
 *
 * Sends the messages around to the clients once all are connected
 * 
 * @author Barry
 */
public class ServerSend {
    
    private ArrayList<ClientSock> clients = null;
    private int client;
    private ServerInfo serverInfo;
    
    public ServerSend(ArrayList<ClientSock> clients, int client, ServerInfo serverInfo){
        this.clients = clients;
        this.client = client;
        this.serverInfo = serverInfo;
        
    }
    
    public void run(){
        Message output = null;
        
        ClientSock clientCon = clients.get(client);
        
        
        try {
            serverInfo.generateKeys();
            
            while(true){
                sendKeys(clientCon);
                
                sendStartRound(clientCon);
                
                output = clientCon.receiveMsg();
                
                if(output != null){
                    
                    if (output.getMsg().equals(CommProtocol.CLIENT_EXIT)){
                        break;
                    } else {
                        serverInfo.addOutput(output);
                        
                        output = null;
                    }
                } else {
                    System.exit(0);
                    
                }
                
                synchronized(serverInfo){
                    if (serverInfo.getNumMsgs() < serverInfo.getNumClients()){
                        try {
                            serverInfo.wait();
                        }   catch(InterruptedException e){
                            
                        
                        }
                    } else {
                        serverInfo.notifyAll();
                    }
                }
                
                sendResults(clientCon);
                
                synchronized(serverInfo){
                    serverInfo.incrementSent();
                    if (serverInfo.getSent() < serverInfo.getNumClients()){
                        try {
                            serverInfo.wait();
                        }   catch(InterruptedException e){
                            
                        
                        }
                    } else {
                        serverInfo.resetCurrentMsg();
                        serverInfo.resetSent();
                        serverInfo.notifyAll();
                    }
                }
                
                Message finalMsg = new Message(CommProtocol.SHUTDOWN);
                
                for (ClientSock c: clients){
                    System.err.println("Shutting down message");
                    c.send(finalMsg);
                }
                
                clientCon.close();
                System.exit(0);
            }
        } catch (EOFException e){
            System.exit(0); //lost connection to client
        } catch (IOException e){
            System.exit(1);
        }
              
    }
    
    public void sendKeys(ClientSock client) throws IOException {
        KeyCollection key = serverInfo.getKeys();
        
        //client.send(key);
        
        Message reply = client.receiveMsg();
        if(reply.getMsg().equals(CommProtocol.ACK)){
            return; //correct results recieved by client
        } else {
            System.exit(0); //failed to send to client
        }
        
    }
    
    public void sendResults(ClientSock client) throws IOException{
        ArrayList<Message> results = new ArrayList<Message>(serverInfo.getCurrentMsg());
        
        client.send(results);
        
        Message reply = client.receiveMsg();
        if(reply.getMsg().equals(CommProtocol.ACK)){
            return; //correct results recieved by client
        } else {
            System.exit(0); //failed to send to client
        }
    }
    
    private void sendStartRound(ClientSock client) throws IOException {
        client.send(new Message(CommProtocol.START_ROUND));
        
    }
    
}
