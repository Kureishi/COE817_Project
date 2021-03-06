package coe817_project;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Sachin
 */

/**
 * Serializable since generated by server to clients during each round
 */
public class KeyCollection implements Serializable {                            // set of keys relating to a client in a round for message broadcasting (generate by server to clients)
    private static final long serialVersionUID = 5671467559046189587L;          // ensure Sender and Receiver loaded classes that compatible with serialization
    private HashSet<Key> keys = new HashSet<Key>();                             // stores keys using hashing/hash table (like array) (store values in a way user not search through to find [use calculation instead of searching])
    
    public KeyCollection() {                                                    // constructor    
    }
    
    /**
     * Adds key to the Key Set
     */
    public void addKey(Key key) {
        keys.add(key);                                                          // add specified key to Key Set (if not already present)
    }
    
    /**
     * Return current key collection
     */
    public Set<Key> getKeyCollection() {                                        // HashSet implements Set Interface (no duplicate elements) -> Key is Set type
        return keys;
    }
    
    public int sum() {
        if (keys != null) {                                                     // if there are keys in collection/set
            int sum = 0;
            for (Key k : keys) {                                                // for all keys in the set
                if(k.getKeyOperation() == KeyOperation.ADD) {                   // if the operation for that key is to add
                    sum += k.getKey();                                          // add it to the sum (from key values)
                }
                else {                                                          // if the operation is to subtract
                    sum -= k.getKey();                                          // subtract it from sum
                }
            }
            
            return sum;                                                         // return the sum
        }
        else {
            return 0;                                                           // if there were no keys, return initial sum
        }
    }
    
    public String toString() {                                                  // how to format the set of keys
        String s = "[";
        for(Key k: keys) {
            s = s + k.toString() + ", ";
        }
        return s + "]";
    }
}
