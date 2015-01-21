/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server.components;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.components.game.LineGoal;
import server.components.game.LineSide;
import server.components.game.Speelveld;
import shared.interfaces.IClient;
import shared.interfaces.IGame;
import shared.observer.RemotePublisher;
import shared.serializable.ChatBericht;

/**
 *
 * @author Michael
 */
public class Toeschouwer extends Persoon implements IGame
{   
    private Game game;
    
    public Toeschouwer(IClient client, String naam, Lobby lobby, Game game) throws RemoteException
    {
        super(client, naam, lobby);
        this.game = game;
    }

    @Override
    public void sendChat(String bericht) throws RemoteException 
    {
        game.sendChat(new ChatBericht(bericht, (Persoon)this));
    }

    @Override
    public RemotePublisher getChatboxRemote() throws RemoteException 
    {
        return (RemotePublisher)game.getChatboxRemote();
    }
    
    @Override
    public void setMoveLeft(boolean moveleft) throws RemoteException 
    {
        //OVERRIDE
    }

    @Override
    public void setMoveRight(boolean moveright) throws RemoteException 
    {
        //OVERRIDE
    }

    @Override
    public int rotatePosition() throws RemoteException 
    {
        return 0;
    }
    
    @Override
    public String getType()
    {
        return "toeschouwer";
    }
    
    public void updateSpeelveld(Speelveld speelveld)
    {
        System.out.println("Versturen Speelveld: " + super.getGebruikersnaam());
        try 
        {
            super.getClient().updateSpeelveld(speelveld);
        } 
        catch (RemoteException ex) 
        {
            Logger.getLogger(Persoon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendLines(LineSide[] sides, LineGoal[] goals)
    {
        try {
            client.setLines(sides, goals);
        } catch (RemoteException ex) {
            Logger.getLogger(Speler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
