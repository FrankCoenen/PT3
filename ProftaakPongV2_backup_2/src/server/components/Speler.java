/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server.components;

import server.components.game.Batje;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.components.Lobby;
import server.components.Persoon;
import server.components.game.Speelveld;
import shared.interfaces.IClient;
import shared.interfaces.IGame;
import shared.observer.RemotePublisher;
import shared.serializable.ChatBericht;

/**
 *
 * @author Michael
 */
public class Speler extends Persoon implements IGame
{
    private final Game game;
    private Batje batje;
    private int score;
       
    private boolean moveLeft = false;
    private boolean moveRight = false;
    
    private int playerNr;
    
    public Speler(IClient client, String naam, Lobby lobby, Game game) throws RemoteException
    {
        super(client, naam, lobby);
        this.game = game;
        batje = null;
        score = 20;
    }
    
    public void setPlayerNr(int nr)
    {
        this.playerNr = nr;
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
    
    public void setBatje(Batje batje)
    {
        this.batje = batje;
    }
    
    public void scorePlus()
    {
        this.score++;
    }
    
    public void scoreMin()
    {
        this.score--;
    }
    
    @Override
    public void setMoveLeft(boolean moveLeft) 
    {
        this.moveLeft = moveLeft;
    }

    @Override
    public void setMoveRight(boolean moveRight) 
    {
        this.moveRight = moveRight;
    }
    
    public boolean getMoveLeft()
    {
        return this.moveLeft;
    }
    
    public boolean getMoveRight()
    {
        return this.moveRight;
    }   
    
    public int getScore()
    {
        return this.score;
    }

    public void update() 
    {
        if(moveLeft)
        {
            batje.moveLeft();
        }
        if(moveRight)
        {
            batje.moveRight();
        }
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

    @Override
    public int rotatePosition() throws RemoteException 
    {   
        return this.playerNr;
    }
    
}
