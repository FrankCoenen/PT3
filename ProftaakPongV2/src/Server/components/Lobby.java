/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server.components;


import Server.sql.controleerPersoonsGegevens;
import Server.sql.registreerPersoonsGegevens;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import shared.interfaces.IClient;
import shared.interfaces.ILobbyLogin;
import shared.interfaces.ILobbySignedIn;

/**
 *
 * @author Michael
 */
public class Lobby extends UnicastRemoteObject implements ILobbyLogin
{
    private static Lobby INSTANCE; 
    private List<Persoon> personen;
    
    private Lobby() throws RemoteException
    {
        personen = new ArrayList<Persoon>();
    }
    
    public static Lobby getInstance() throws RemoteException
    {
        if(INSTANCE == null)
        {
            INSTANCE = new Lobby();
        }
        
        return INSTANCE;
    }

    @Override
    public ILobbySignedIn login(IClient client, String naam, String wachtwoord) 
    {
        Boolean result = false;
        controleerPersoonsGegevens login = new controleerPersoonsGegevens(naam, wachtwoord);
        ExecutorService task = Executors.newSingleThreadExecutor();
        Future<Boolean> future = task.submit(login);
        try 
        {
             result = future.get();
        } 
        catch (InterruptedException ex) 
        {
            Logger.getLogger(Lobby.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (ExecutionException ex) 
        {
            Logger.getLogger(Lobby.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        task.shutdown();
        
        if(result)
        {
            Persoon p = new Persoon(client,naam);
            this.addPersoon(p);
            return p;
        }
        else
        {
            return null;
        }
    }

    @Override
    public boolean register(String naam, String wachtwoord) 
    {
        registreerPersoonsGegevens register = new registreerPersoonsGegevens(naam, wachtwoord);
        Thread t = new Thread(register);
        
        try
        {
            t.start();
        }
        catch(Exception E)
        {
            return false;   
        }
        finally
        {
            return true;   
        }
    }
    
    private synchronized void addPersoon(Persoon p)
    {
        if(!personen.contains(p))
        {
            personen.add(p);
        }
    }
} 
