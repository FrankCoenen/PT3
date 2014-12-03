/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server.components;


import java.util.ArrayList;
import shared.interfaces.IClient;
import shared.interfaces.IGame;
import shared.interfaces.ILobbyLogin;
import shared.interfaces.ILobbySignedIn;
import shared.observer.RemotePublisher;

/**
 *
 * @author Michael
 */
public class Lobby implements ILobbyLogin
{

    @Override
    public ILobbySignedIn login(IClient client, String naam, String wachtwoord) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
} 
