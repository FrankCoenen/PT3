/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server.components;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import shared.interfaces.ILobbyLogin;




/**
 *
 * @author Merijn
 */
public class ServerStart 
{
    
    private static final int portNumber = 1100;

    // Set binding name for student administration
    private static final String bindingName = "lobby";

    // References to registry and student administration
    private Registry registry = null;
    private ILobbyLogin lobby = null;
    
    public ServerStart()
    {
        // Print port number for registry
        System.out.println("Server: Port number " + portNumber);

        // Create student administration
        try 
        {
            lobby = (ILobbyLogin)Lobby.getInstance();
            System.out.println("Server: lobby created");
        } catch (RemoteException ex) 
        {
            System.out.println("Server: Cannot create lobby");
            System.out.println("Server: RemoteException: " + ex.getMessage());
        }

        // Create registry at port number
        try {
            registry = LocateRegistry.createRegistry(portNumber);
            System.out.println("Server: Registry created on port number " + portNumber);
        } catch (RemoteException ex) {
            System.out.println("Server: Cannot create registry");
            System.out.println("Server: RemoteException: " + ex.getMessage());
            registry = null;
        }

        // Bind student administration using registry
        try {
            registry.rebind(bindingName, lobby);
        } catch (RemoteException ex) {
            System.out.println("Server: Cannot bind lobby");
            System.out.println("Server: RemoteException: " + ex.getMessage());
        }
    }
    
    public static void main(String[] args)
    {
        ServerStart start = new ServerStart();
    }
}
