package coe817_project;

/**
 * Defines messages that possible to send between Server and its Clients
 * 
 * @author Sachin
 */
public class CommProtocol {                                                     // Declare Set of Constants
    
    public static final String KEY_REQUEST = "KEY";                             // Client request Key Set from Server for the round that going to start
    
    public static final String ACK = "OK";                                      // Acknowledgement (usually from client to server)
    
    public static final String START_ROUND = "STARTROUND";                      // Message from Server to Client indicate start of round
    
    public static final String CLIENT_EXIT = "KILL";                            // Message from Client to Server indicate Client quit session
    
    public static final String SHUTDOWN = "END";                                // Message from Server to Clients indicate they should quit
}
