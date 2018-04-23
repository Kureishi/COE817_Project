/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package coe817_project;

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
    
    public ServerSend(ArrayList<ClienSock> clients, int client, ServerInfo serverInfo){
        this.clients = clients;
        this.client = client;
        this.serverInfo = serverInfo;
        
    }
    
    public void run(){
        Message output = null;
        
        ClientSock clientCon = clients.get(client);
        
    }
    
}
