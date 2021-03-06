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
public class Speler extends Persoon implements IGame
{
    /**
     * variabelen met logische naam
     */
    private final Game game;
    protected Batje batje;
    private int score;
       
    private boolean moveLeft = false;
    private boolean moveRight = false;
    
    private int playerNr;
    /**
     * constuctor
     * @param client
     * @param naam
     * @param lobby
     * @param game
     * @throws RemoteException 
     */
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
    /**
     * score erbij
     */
    public void scorePlus()
    {
        this.score++;
    }
    /**
     * score eraf
     */
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
    /**
     * check of je naar links beweegt
     * @return true of false
     */
    public boolean getMoveLeft()
    {
        return this.moveLeft;
    }
     /**
     * check of je naar rechts beweegt
     * @return true of false
     */
    public boolean getMoveRight()
    {
        return this.moveRight;
    }   
    
    public int getScore()
    {
        return this.score;
    }
    /**
     * update van de batjes bewegen
     */
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
    
    /**
     * speelvelden updaten op de clients
     * @param speelveld speelveld
     * @throws RemoteException remote class
     */
    public void updateSpeelveld(Speelveld speelveld) throws RemoteException
    {
        System.out.println("Versturen Speelveld: " + super.getGebruikersnaam());
        try 
        {
            super.getClient().updateSpeelveld(speelveld);
        } 
        catch (RemoteException ex) 
        {
            Logger.getLogger(Persoon.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;           
        }
    }

    @Override
    public int rotatePosition() throws RemoteException 
    {   
        return this.playerNr;
    }
    
    @Override
    public String getType()
    {
        return "speler";
    }
    /**
     * bereken van eindscore
     * @param score1
     * @param score2
     * @return
     * @throws RemoteException 
     */
    public boolean berekenEindScore(int score1, int score2) throws RemoteException
    {
        int rating = (int) this.getRating(super.getGebruikersnaam());
        int berekening = this.score +(score1 + score2 - 2*rating)/8;
        return super.lobby.nieuweScore(super.getGebruikersnaam(), berekening);
    }
    /**
     * zenden van de zijkanten van veld
     * @param sides
     * @param goals 
     */
    public void sendLines(LineSide[] sides, LineGoal[] goals)
    {
        try {
            client.setLines(sides, goals);
        } catch (RemoteException ex) {
            Logger.getLogger(Speler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * AI neemt over bij dc.
     * @return 
     */
    public AI becomeRobot()
    {
        try {
            AI ai = new AI(null,"IAmRobot" + this.playerNr, this.lobby, this.game, this.game.getSpeelveld().getBall());
            ai.setBatje(batje);
            return ai;
        } catch (RemoteException ex) {
            Logger.getLogger(Speler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
