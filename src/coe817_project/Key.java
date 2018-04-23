package coe817_project;

import java.io.Serializable;

/**
 * Key shared between 2 clients
 * 
 * @author Sachin
 */
public class Key implements Serializable{
    
    private static final long serialVersionUID = -709609931265527197L;          // since implementing serializable
    
    private final int key;                                                      // declare variables
    private final KeyOperation kOp;
    
    public Key(int key, KeyOperation kOp) {                                     // instantiate with constructor
        this.key = key;
        this.kOp = kOp;
    }
    
    public int getKey() {                                                       // get requested Key
        return this.key;
    }
    
    public KeyOperation getKeyOperation() {                                     // get Key Operation (add or subtract)
        return this.kOp;
    }
    
    /**
     * Generate Random Key Value
     */
    public static Key generateRandKey() {                                       // generate Random Key with KeyOperation=null (not needed)
        return new Key (RandomGenerator.generateInteger(), null);               // used to get a SecureRandom integer
    }
    
    @Override
    public String toString() {                                                  // how to represent textual representation of keys
        String str = "(" + key + ",";                                           // beginning of key expression
        
        if(kOp == KeyOperation.ADD) {                                           // if the key operation is to add (add keys)
            str = str + "+";                                                    // add the key to set
        }
        else {
            str = str + "-";                                                    // substract key from set
        }
        
        return str + ")";                                                       // return String
    }
}
