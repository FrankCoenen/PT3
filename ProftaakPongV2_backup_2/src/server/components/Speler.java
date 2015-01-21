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
     * variabelen met een logische naam
     */
    private final Game game;
    protected Batje batje;
    private int score;
       
    private boolean moveLeft = false;
    private boolean moveRight = false;
    
    private int playerNr;
    /**
     * constructor
     * @param client client van de speler
     * @param naam naam van de speler
     * @param lobby lobby 
     * @param game de game
     * @throws RemoteException omdat het een remote class is 
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
     * als je scoort komt er een punt bij
     */
    public void scorePlus()
    {
        this.score++;
    }
    /**
     * wordt er bij je gescoort dan gaat er een punt af
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

    /**
     * update voor als je batje beweegt
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
     * update eht speelveld met de nieuwe posities van de batjes
     * @param speelveld het speelveld
     * @throws RemoteException omdat het een romote class is
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
     * het berekenen van de score ana het eind van een potje voor de nieuwe rating
     * @param score1 score
     * @param score2 score
     * @return een boolean of het gelukt is of niet
     * @throws RemoteException omdat de class remote is
     */
    public boolean berekenEindScore(int score1, int score2) throws RemoteException
    {
        int rating = (int) this.getRating(super.getGebruikersnaam());
        int berekening = this.score +(score1 + score2 - 2*rating)/8;
        return super.lobby.nieuweScore(super.getGebruikersnaam(), berekening);
    }
    
    /**
     * over sturen van de zijde van het speelveld waarin je speelt
     * @param sides de zijkanten
     * @param goals  de goals
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
     * als er een speler in het midden van een game leaved neemt een AI het over
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
