package coe817_project;

import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Loop (operations) client goes through during its session with server
 * 
 * @author Sachin
 */
public class DiningAlgoLoop implements Input{
    private final static int MAX_CHARACTER = '\uffff';                          // unicode max (max 16-bit value)
    private final ClientCon connection;
//    private Output out = null;
    
    private StringBuffer inBuff = new StringBuffer(20);                         // specify array/buffer length to be 20 
    private String currMessage = null;                                          // the message working on
    private int currMessageIndex = 0;                                           // index of the current message
    
    public DiningAlgoLoop(ClientCon connection) {                               // Constructor to instantiate conversation
        this.connection = connection;                                           // instatiate variables
//        this.out = output;
    }
    
    public void start() {                                                       // start the loop
        Message receive;
        ArrayList<Message> roundRes;
        boolean inMessageFlag = false;
        
        while(true) {                                                           // while nothing to break loop
            try {  
                KeyCollection keys = getKeyCollection();                        // get the set of keys                                        
                
                if (keys == null) {                                             // something wrong or receive command to shut down
                    break;
                }
                
                receive = connection.receiveMessage();                          // configure to receive messages
                
                if(receive.getMsg().equals(CommProtocol.START_ROUND)) {         // if the received message indicated a "START_ROUND" message
                    transmit(getNextMessageChar(), keys);
                    
                    roundRes = connection.receiveSessionResults();              // wait for results from round...
                    if(roundRes.size() == 0) {
                        System.err.println("RoundResults have size 0");         // print error
                        System.exit(0);
                    }
                    
                    connection.send(new Message(CommProtocol.ACK));             // else, acknowledge result been received
                    
                    char res = collate(roundRes);                               // collate(collect) results together from the round -> compare strings based on method-defined structure
                    
                    if(res != 0) {                                              // if a result exists (messages present)
                        System.out.println(res);                                // display result
                        inMessageFlag = true;                                   // set Flag to true
                    } 
                    else if(inMessageFlag) {                                    // if inMessageFlag = true -> skip line
                        System.out.println("\n");
                        inMessageFlag = false;                                  // reset flag
                    }
                }
                else if(receive.getMsg().equals(CommProtocol.SHUTDOWN)) {       // Server indicate want Clients to quit
                    break;
                }
                else {
                    System.err.println("Server request unexpected");            // not in Communication Protocol (print to error stream)
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            }        
        }
    }
    
    public char collate(ArrayList<Message> message_set) {
        int sum = 0;
        
        for(Message m: message_set) {                                           // for the messages in the list
            sum += Integer.parseInt(m.getMsg());                                // parse through the message and return an int to add to sum (each message) -> give idea of amount messages
        }
        
        if(sum == 0) {                                                          // size 0 -> number of messages 0
            System.out.println("No message transmitted");
        }
        else if(sum >= 2*MAX_CHARACTER) {                                       // if higher than double Max, means added around same time
            System.out.println("Collision occured");
        }
        else {
            sum -= MAX_CHARACTER;                                               // keep subtracting max character value from it
        }
        
        return (char)sum;                                                       // return sum as character
    }
    
    private KeyCollection getKeyCollection() throws IOException {               // private (only this class) as to not confuse with the one in KeyCollection class
        KeyCollection keys = null;                                              // get collection of keys currently
        
        try{
            keys = connection.receiveKeyCollection();                           // read the collection from stream
        }
        catch(ClassNotFoundException e) {                                       // catch exception
            return null;
        }
        
        connection.send(new Message(CommProtocol.ACK));                         // acknowledgement sent to server as indication that client receive collection of keys
        
        return null;
    }
    
    private char getNextMessageChar() {
        if(currMessage == null) {
            currMessage = inBuff.next();                                        // if the current message is null (not occupied), take next String in buffer and fill it
        }
        
        if(currMessage == null) {                                               // if there is nothing to be next in the buffer...
            return (char)0;                                                     // return the character form of 0
        }
        else {
            if(currMessageIndex < currMessage.length()) {                       // as long as there are still parts of message to process
                char res_c = currMessage.charAt(currMessageIndex);              // assign the character currently processing and stored in res_c (result character)
                currMessageIndex++;                                             // increment index to get next character of message next time
                return res_c;                                                   // return res_c
            }
            else {
                currMessage = null;                                             // once message down (no more characters) -> make the message null
                currMessageIndex = 0;                                           // instantiate index back to 0 for next message
                    
                return (char)0;                                                 // return 0 as character
            }
        }
    }
    
    private void transmit(char c, KeyCollection kColl) throws IOException {
        int cRes = c;                                                           // assign the character to transmit to cRes (to make able do integer add)
        
        if(c != 0) {                                                            // non-zero character (actual character)
            cRes += MAX_CHARACTER;                                              // add max (to detect collision)
        }
        
        int out_send = kColl.sum() + cRes;                                      // collect the summation of keys and the character added value
        connection.send(new Message("" + out_send));                            // send it to the stream
    }
    
    @Override
    public void inputStr(String str) {                                          // how to format the string
        inBuff.add(str);                                                        // add string to buffer
        System.out.println("Message: " + str + "has been added to buffer.");    // format it
    }
}
