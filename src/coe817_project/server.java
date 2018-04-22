/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package coe817_project;

import java.io.IOException;
import java.util.ArrayList;



/**
 *
 * @author Barry
 */
public class server {
    
    private static final int PORT = 21212;
    private static final int MAXCLIENTS = 1;
    
    public static void main(String[] args) throws IOException {
        ServerCon connection;
        if (args.length == 1){
            connection = new ServerCon(new Integer(args[0]).intValue());
        
        } else {
            connection = new ServerCon(PORT);
        }
        System.out.println("Server started, please connect on Port: " + PORT);
        ArrayList<ClientSock> clients = new ArrayList<ClientSock>();
        int numReplies = 0;
        //Sharedserver
        
        
        
    }
    
}
