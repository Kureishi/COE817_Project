package coe817_project;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;


/**
 *
 * Initiates server connection and waits for client to connect.
 * 
 * @author Barry
 */
public class ServerCon {
    private static final int portNumber = 21212;
    private ServerSocket sock = null;
    Socket serviceSocket = null;
    DataInputStream inputStream = null;
    DataOutputStream outputStream = null;
    
    
    public ServerCon(int port){
        try {
            sock = new ServerSocket(port);
            serviceSocket = sock.accept();
            inputStream = new DataInputStream(serviceSocket.getInputStream());
            outputStream = new DataOutputStream(serviceSocket.getOutputStream());
            
        } catch (IOException e){
            System.out.println(e);
            System.exit(1);
        }
    }
    
    public ClientSock acceptConnection(){
        try{
            ClientSock cS = new ClientSock(sock.accept());
            return cS;
            } catch (IOException e) {
                System.exit(1);
            }
        return null;
    }
    
    public void disconnect() throws IOException {
        sock.close();
    }
}
