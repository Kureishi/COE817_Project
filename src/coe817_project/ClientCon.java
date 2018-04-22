package coe817_project;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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
    
    public ClientCon(String serverAddr, int serverPort) {                       // instantiate constructor to connect to specified server (instantiate variables)
        this.serverAddr = serverAddr;
        this.serverPort = serverPort;
    }
    
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
    
}
