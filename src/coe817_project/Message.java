/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package coe817_project;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * Using a serializable object to hold the message to be passed between server
 * and clients. (makes the message into a series of bytes)
 * 
 * @author Barry
 */
public class Message implements Serializable {
    
    private String message;
    private char singleMsg;
 //   private final ArrayList<Key> keys;
    private static final int base = (int) '@';
    private static final int alphaSize = 27;
    
    public Message(String message){
        this.message = message;
        
    }
    
    public String getMsg(){
        return this.message;
    }
    public char getSinglemsg(){
        return (char)(this.singleMsg + base);
    }
    
    //need to implement encryption here?
    
    public char combineMessages(List<Character> messages){
        char temp = this.singleMsg;
        for (Character m: messages){
            int offset = m.charValue() - base;
            temp = (char) (((temp + offset)+alphaSize)%alphaSize);
        }
        return (char)(temp + base);
    }
    
}
