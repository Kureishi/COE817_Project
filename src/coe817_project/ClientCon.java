package coe817_project;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.net.UnknownHostException;
import java.net.ConnectException;


/**
 *
 * Initiates Client Connection
 * 
 * @author Sachin
 */
public class ClientCon {
    
    private String serverAddr;                                                  // initialize variables (use standard socket to connect to server)
    private int serverPort;
    private Socket sock = null;
    ObjectInputStream inStream = null;                                          // represents that Java Objects (implements Serializable and Externalizable) in streams
    ObjectOutputStream outStream = null;
    
    /**
     * Constructor
     */
    public ClientCon(String serverAddr, int serverPort) {                       // instantiate constructor to connect to specified server (instantiate variables)
        this.serverAddr = serverAddr;
        this.serverPort = serverPort;
    }
    
    /**
     * Connect to server specified in Client Class and report any errors
     */
    public String connection() {
        try {
            sock = new Socket(serverAddr, serverPort);                          // connect to specific server
        }
        catch (UnknownHostException e) {                                        // if IP of host can't be determined
            return new String ("Unknown Host");
        }
        catch (ConnectException e) {                                            // error while trying to connect socket to remote address and port
            return new String ("Server not running");                           // usually refused remotely (no process listening on remote address/port)
        }
        catch (IOException e) {                                                 // failed or interrupted I/O operations 
            e.printStackTrace();
            System.exit(1);                                                     // catchall for general errors
        }
        
        return new String ("");                                                 // represent error length = 0 -> successful connection
    }
    
    /**
     * Send single message to server (connected to stream)
     */
    public void send(Message message) throws IOException {                      // Message is root interface of all JMS messages, and acknowledge method used for all messages
        outStream.writeObject(message);                                         // send message as Java object
    }
    
    /**
     * Receive Message Object from server
     */
    public Message receiveMessage() throws IOException {
        Message newMessage = null;                                              // initialize new message (that received from Server)
        
        try {
            newMessage = (Message)inStream.readObject();                        // read the message from the stream
        }
        catch(ClassNotFoundException e) {                                       // when try to load class through string name, but no definition for it found
            return null;
        }
        return newMessage;                                                      // return the message that was read
    }
    
    /**
     * Receive a KeyCollection object from Server
     */
    public KeyCollection receiveKeyCollection() throws IOException, ClassNotFoundException {
        KeyCollection kSet = null;
        
        kSet = (KeyCollection) inStream.readObject();                           // read in object (from server on other end) and cast to KeyCollection (since terminal in bytes)
        
        return kSet;                                                            // return the KeyCollection object
    }
    
    @SuppressWarnings("unchecked")                                              // suppress "unchecked" warnings (also in all containing elements)
    public ArrayList<Message> receiveSessionResults() throws IOException{
        ArrayList<Message> message = null;                                      // store messages in an array list
        
        try {
            message = (ArrayList<Message>) inStream.readObject();               // get from the stream and cast it into a message to put in array list
        }
        catch(ClassNotFoundException e) {                                       // catch exception
            return null;
        }
        
        return message;                                                         // return message received
    }
    
    /**
     * Disconnect client from server
     */
    public void disconnect() {
        try {
            inStream.close();                                                   // close stream and socket
            outStream.close();
            sock.close();
        }
        catch(IOException e) {                                                  // catch exception
            e.printStackTrace();
        }
    }
    
    @Override
    public String toString() {                                                  // override toString method
        return serverAddr + ":" + serverPort;
    } 
     
}
