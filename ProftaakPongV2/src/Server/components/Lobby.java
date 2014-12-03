/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server.components;


import shared.interfaces.IClient;
import shared.interfaces.ILobbyLogin;
import shared.interfaces.ILobbySignedIn;

/**
 *
 * @author Michael
 */
public class Lobby implements ILobbyLogin
{
    private static Lobby INSTANCE;
    
    
    private Lobby()
    {
        
    }
    
    public static Lobby getInstance()
    {
        if(INSTANCE == null)
        {
            INSTANCE = new Lobby();
        }
        
        return INSTANCE;
    }

    @Override
    public ILobbySignedIn login(IClient client, String naam, String wachtwoord) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
} 
