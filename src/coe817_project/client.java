package coe817_project;

/**
 *
 * @author Sachin
 */
public class client {
    
    private static ClientCon conn = null;                                       // an object of ClientConn used to connect to server
    
    public static void main(String[] args) {
        
        if(args.length == 2) {                                                  // if entered by user in terminal, for a specific server/port #
            conn = new ClientCon(args[0], new Integer(args[1]).intValue());     // since args is text, use intValue() to convert to integer
        }
        else {
            conn = new ClientCon("localhost", 21212);                           // else, just connect to server configured here
        }
        
        System.out.println("Connect to: " + conn.toString());                   // display server info that connecting to     
        String err = conn.connection();                                         // to determine if error during connection process
        if (err.length() != 0) {                                                // if there is error message
            System.out.println(err);                                            // display error message in terminal
            System.exit(1);                                                     // change exit code to 1 -> catchall for general errors
        }
        
        DiningAlgoLoop loop = new DiningAlgoLoop(conn);                         /*****MIGHT NEED TO ADD OUTPUT TO REVERT TO ORIGINAL CONSTRUCTOR******/
        System.out.println(loop.toString());                                    /*****CODE TO SET THE TEXT TO BE INPUT********************************/
        
        loop.start();                                                           // start the main loop for client(s) and server communication
        conn.disconnect();                                                      // afterwards, disconnect from connection when finished
        
        System.exit(0);                                                         // if reach here, successful run
    }
    
}
