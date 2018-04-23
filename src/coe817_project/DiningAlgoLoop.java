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
    private final static int MAX_CHARACTER = '\uffff';                          // unsigned (max value)
    private final ClientCon connection;
//    private Output out = null;
    
    private StringBuffer inBuff = new StringBuffer(20);                         // specify array/buffer length to be 20 
    private String currMessage = null;                                          // the message working on
    private int currMessageIndex = 0;                                           // index of the current message
    
    public DiningAlgoLoop(ClientCon connection) {                               // Constructor to instantiate conversation
        this.connection = connection;                                           // instatiate variables
//        this.out = output;
    }
    
    public void run() {
        Message receive;
        ArrayList<Message> roundRes;
        boolean inMessageFlag = false;
        
        while(true) {
            try {  
                KeyCollection keys = getKeyCollection();                        // get the set of keys                                        
                
                if (keys == null) {                                             // something wrong or receive command to shut down
                    break;
                }
                
                receive = connection.receiveMessage();                          // configure to receive messages
                
                if(receive.getMsg().equals(CommProtocol.START_ROUND)) {         // if the received message indicated a "START_ROUND" message
                    transmit(getNextChar(), keys);
                    
                    roundRes = connection.receiveSessionResults();              // wait for results from round...
                    if(roundRes.size() == 0) {
                        System.err.println("RoundResults have size 0");         // print error
                        System.exit(0);
                    }
                    
                    connection.send(new Message(CommProtocol.ACK));             // else, acknowledge result been received
                    
                    char res = collate(roundRes);                               // collate(collect) results together from the round
                    
                    if(res != 0) {                                              // if a result exists
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
        
        for(Message m: message_set) {
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
        KeyCollection keys = null;
        
        
    }
    
    
    
    @Override
    public void inputStr(String str) {
        
    }
}
