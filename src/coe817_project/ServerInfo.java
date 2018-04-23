/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package coe817_project;

import java.util.ArrayList;


/**
 *
 * Server information shared among clients
 * 
 * @author Barry
 */
public class ServerInfo {
    
    private int numSent = 0;
    private int numClients, amtKeys = 0;
    private ArrayList<Message> currentMsgs;
    private KeyCollection[] keys = null;
    
    public ServerInfo(int numReplies, int numClients, ArrayList<Message> currentMsgs){
        this.numSent = numReplies;
        this.numClients = numClients;
        this.currentMsgs = currentMsgs;
        
    }
    
    public synchronized void incrementSent(){
        this.numSent++;
    }
    
    public synchronized void resetSent(){
        this.numSent = 0;
        
    }
    
    public synchronized int getSent(){
        return this.numSent;
    }
    
    public synchronized int getNumClients(){
        return this.numClients;
    }
    
    public synchronized int getNumMsgs(){
        return this.currentMsgs.size();
    }
    
    public synchronized ArrayList<Message> getCurrentMsg(){
        return this.currentMsgs;
    }
    
    public synchronized void addOutput(Message message){
        this.currentMsgs.add(message);
        
    }
    
    public synchronized void resetCurrentMsg(){
        this.currentMsgs.clear();
    }
    
    public synchronized KeyCollection getKeys(){
        if (amtKeys == numClients){
            generateKeys();
            amtKeys = 0;
            
        }
        
        KeyCollection set = keys[amtKeys];
        amtKeys++;
        
        return set;
    }
    
    public void generateKeys(){
        keys = new KeyCollection[numClients];
        for (int i=0; i<numClients; i++){
            for(int j=i+1;j<numClients;j++){
                
                //key generation
                Key key = Key.generateRandKey();
                
                Key keyop = new Key(key.getKey(), KeyOperation.ADD);
                Key keyn = new Key(key.getKey(), KeyOperation.SUB);
                
                keys[i].addKey(keyop);
                keys[j].addKey(keyn);
                
            }
        }
    }
           
}
