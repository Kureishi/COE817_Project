package coe817_project;

/**
 *
 * @author Sachin
 */
public class StringBuffer {                                                     // in order to store messages in a buffer (to read/write messages)
    
    private final static int DEF_LENGTH = 10;                                   // default length of the buffer
    
    private String[] arr = null;                                                // declare and instantiate variables
    private int len = DEF_LENGTH;
    private int readPos = 0;
    private int writePos = 0;   
    
    /**
     * Constructor
     * 
     * @param buffLength -> length of buffer
     */
    public StringBuffer(int buffLength) {
        len = buffLength;                                                       // instantiate length
        arr = new String[len];                                                  // declare array to length-elements
        
        for (int i=0; i < len; i++) {                                           // instantiate elements of array to null
            arr[i] = null;
        }
    }
    
    /**
     * Constructor
     */
    public StringBuffer() {
        this(DEF_LENGTH);                                                       // this type of Constructor initialize array to default length (instead of user-specified)
    }
    
    public void add(String str) {
        arr[writePos] = str;                                                    // add (write) a string to the array
        writePos++;                                                             // increment write position index for next write to be in new position
    }
    
    public String next() {
        String rArr = arr[readPos];                                             // take message to be read and store in rArr
        arr[readPos] = null;                                                    // make the message at original index null (until updated with more messages)
         
        if(rArr != null) {                                                      // if there is message to be read
            readPos++;                                                          // increment position index to read from
        }
        
        return rArr;                                                            // return the message to be read (in rArr)
    }
}
