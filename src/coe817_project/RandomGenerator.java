package coe817_project;

import java.security.SecureRandom;                                              // provide a cryptographically strong (RFC 1750) random number generator (seed object is unpredictable)

/**
 *
 * @author Sachin
 */
public class RandomGenerator {
    
    public static int generateInteger() {
        SecureRandom rand = new SecureRandom();
        
        byte[] b = new byte[4];                                                 // used in formulation of generating random bytes
        rand.nextBytes(b);                                                      // generate user-specified number of random bytes (if first call, force SecureRandom object to seed itself)
        
        return  (new Byte(b[0])).intValue() +                                   // formulation to generate random integer
                (new Byte(b[1])).intValue()*Byte.MAX_VALUE +                    
                (new Byte(b[1])).intValue()*Byte.MAX_VALUE*Byte.MAX_VALUE + 
                (new Byte(b[1])).intValue()*Byte.MAX_VALUE*Byte.MAX_VALUE*Byte.MAX_VALUE;			
    }
    
}
