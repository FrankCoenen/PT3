/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server.components;

import java.io.Serializable;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import shared.interfaces.IClient;
import shared.interfaces.IGame;
import shared.interfaces.ILobbySignedIn;
import shared.observer.RemotePublisher;
import shared.serializable.ChatBericht;

/**
 *
 * @author Merijn
 */
public class Persoon extends UnicastRemoteObject implements ILobbySignedIn, Serializable 
{
    private transient IClient client;
    private String naam;
    private transient Lobby lobby;
    private transient GameLobby gameLobby;
    
    public Persoon(IClient client, String naam, Lobby lobby) throws RemoteException
    {
        this.client = client;
        this.naam = naam;
        this.lobby = lobby;
    }

    @Override
    public IGame StartGame(IClient client) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RemotePublisher getChatbox() {
        return (RemotePublisher)lobby.getChatBox();
    }

    @Override
    public void sendChat(String bericht) {
        lobby.getChatBox().addBericht(new ChatBericht(bericht,(Persoon)this));
    }

    @Override
    public boolean CreateGame() {
        if (this.gameLobby != null)
        {
            this.gameLobby = lobby.createGame(this);
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public RemotePublisher JoinGame() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IGame spectateGame() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getGebruikersnaam() {
        return this.naam;
    }
    
    public IClient getClient()
    {
        return client;
    }
}
