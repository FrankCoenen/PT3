/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.components;

import client.communications.ChatBoxListener;
import client.gui.GameFXController;
import client.gui.LobbyFXController;
import client.gui.LoginFXController;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import shared.interfaces.IClient;
import shared.interfaces.IGame;
import shared.interfaces.ILobbyLogin;
import shared.interfaces.ILobbySignedIn;

/**
 *
 * @author Michael
 */
public class Client extends UnicastRemoteObject implements IClient
{
    private static LoginFXController loginFXController;
    private static LobbyFXController lobbyFXController;
    private static GameFXController gameFXController;
    
    private ChatBoxListener lobbyChatListener;
    private ChatBoxListener gameChatListener;
    
    private static Client INSTANCE;
    
    private IGame game;
    private ILobbySignedIn lobby;
    private ILobbyLogin login;
    
    private Registry registry;
    
    private Client() throws RemoteException
    {
        INSTANCE = this;
        
        try
        {
            registry = LocateRegistry.getRegistry("localhost", 1099);
        }
        catch(RemoteException e)
        {
                     
        }
        try
        {
            login = (ILobbyLogin)registry.lookup("lobby");
        }
        catch(NotBoundException | RemoteException e)
        {
                         
        }
    }
    
    public static Client getInstance(LoginFXController controller) throws RemoteException
    {
        if(INSTANCE == null)
        {
            new Client();
        }
        loginFXController = controller;
        return INSTANCE;
    }
    
    public static Client getInstance(LobbyFXController controller) throws RemoteException
    {
        if(INSTANCE == null)
        {
            new Client();
        }
        lobbyFXController = controller;
        return INSTANCE;
    }
    
    public static Client getInstance(GameFXController controller) throws RemoteException
    {
        if(INSTANCE == null)
        {
            new Client();
        }
        gameFXController = controller;
        return INSTANCE;
    }
    
    public void logIn(String gebruikersnaam, String wachtwoord)
    {
        try 
        {
            lobby = login.login(this, gebruikersnaam, wachtwoord);
            
            if(lobby == null)
            {
                System.out.println("Je moeder werkt niet met deze lobby = NULL BITCH");
            }
            else
            {
                openLobbyGUI();
            }
            
        } 
        catch (RemoteException ex) 
        {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void openLobbyGUI()
    {
        System.out.println("Probeert LobbyGUI te openen Platform.runLater");
        Platform.runLater(new Runnable(){

            @Override
            public void run() 
            {
                loginFXController.openLobbyGUI();
            }
        });
    }
}
