/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server.components;

import shared.interfaces.IClient;
import shared.interfaces.IGame;
import shared.interfaces.ILobbySignedIn;
import shared.observer.RemotePublisher;

/**
 *
 * @author Merijn
 */
public class Persoon implements ILobbySignedIn {
    private IClient client;
    private String naam;
    
    public Persoon(IClient client, String naam)
    {
        this.client = client;
        this.naam = naam;
    }

    @Override
    public IGame StartGame(IClient client) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RemotePublisher getChatbox() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void sendChat(String bericht) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RemotePublisher CreateGame() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RemotePublisher JoinGame() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
