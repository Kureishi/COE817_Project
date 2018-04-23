package coe817_project;

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
    
    
    
    @Override
    public void inputStr(String str) {
        
    }
}
