/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package proftaakpong_server;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author Michael
 */
public class RMIServer 
{
    // Set port number
    private static final int portNumber = 1099;

    // Set binding name for student administration
    private static final String bindingNameGame = "Game";

    // References to registry and student administration
    private Registry registry = null;
    //private MockEffectenBeurs beurs = null;
    
    // Constructor
    public RMIServer() 
    {

        // Print port number for registry
//        System.out.println("Server: Port number " + portNumber);
//
//        // Create student administration
//        try 
//        {
//            //beurs = new MockEffectenBeurs();
//            System.out.println("Server: EffectenBeurs created");
//        } 
//        catch (RemoteException ex) 
//        {
//            System.out.println("Server: Cannot create EffectenBeurs");
//            System.out.println("Server: RemoteException: " + ex.getMessage());
//        }

        // Create registry at port number
        try 
        {
            registry = LocateRegistry.createRegistry(portNumber);
            System.out.println("Server: Registry created on port number " + portNumber);
        } 
        catch (RemoteException ex) {
            System.out.println("Server: Cannot create registry");
            System.out.println("Server: RemoteException: " + ex.getMessage());
            registry = null;
        }

        // Bind student administration using registry
//        try 
//        {
//            registry.rebind(bindingName, /*beurs*/);
//        } 
//        catch (RemoteException ex) {
//            System.out.println("Server: Cannot bind student administration");
//            System.out.println("Server: RemoteException: " + ex.getMessage());
//        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        // Welcome message
        System.out.println("STARTING RMI SERVER");

        // Create server
        RMIServer server = new RMIServer();
        
        // Welcome message
        System.out.println("SERVER USING NAMING: " + server.toString());
    }
}
