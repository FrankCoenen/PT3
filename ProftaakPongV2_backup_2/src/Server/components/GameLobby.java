/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server.components;

import server.components.Game;
import server.components.Speler;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Merijn
 */
public class GameLobby 
{
    /**
     * variabelen met logische namen
     */
    private String name; 
    private List<Persoon> spelers;
    private int spotsLeft;
    private Boolean hasStarted;
    private List<Persoon> spectators;
    private Game game;
    private Persoon creator;
    
    /**
     * constructor
     * @param creator 
     */
    public GameLobby(Persoon creator)
    {
        this.name = creator.getGebruikersnaam() + "'s Game";
        this.spectators = new ArrayList();
        this.spelers = new ArrayList();
        this.spotsLeft = 3;
        this.hasStarted=false;
        this.creator = creator;
        this.addSpelers(creator);
        game = null;
    }
    
    
    public Persoon getCreator() {
        return creator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Persoon> getSpelers() {
        return spelers;
    }

    /**
     * spelers toevoegen aan een gamelobby 
     * @param spelers 
     */
    public void addSpelers(Persoon spelers) 
    {
        if(spotsLeft > 0 && this.hasStarted == false)
        {
            this.spelers.add(spelers);
            System.out.println("Speler toegevoegd aan GameLobby: " + spelers.getGebruikersnaam());
            this.updateGUI();
            this.spotsLeft--;
        }
        else
        {
            
        }
    }
    /**
     * spectators toevoegen aan een game
     * @param spectators de touschouewrs
     */
    public void addSpectators(Persoon spectators)
    {
        this.spectators.add(spectators);
        System.out.println("Speler toegevoegd aan GameLobby: " + spectators.getGebruikersnaam());
        this.updateGUI();
        
        if(hasStarted)
        {
            Toeschouwer t = spectators.getToeschouwer(this.game);
            this.game.addToeschouwer(t);
            spectators.notifyGameStart(t);
            t.sendLines(game.getSpeelveld().getLineSides(), game.getSpeelveld().getGoals());
        }
    }

    public int getSpotsLeft() {
        return spotsLeft;
    }

    public void setSpotsLeft(int spotsLeft) 
    {
        this.spotsLeft = spotsLeft;
    }

    public Boolean getHasStarted() {
        return hasStarted;
    }

    public void setHasStarted(Boolean hasStarted) {
        this.hasStarted = hasStarted;
    }

    public List<Persoon> getSpectators() 
    {
        return spectators;
    }
    /**
     * update van gui als een nieuw persoon de lobby joint 
     */
    public void updateGUI()
    {
        ArrayList<String> personen = new ArrayList<>();
        ArrayList<String> toeschouwers = new ArrayList<>();
                
        for(Persoon p : this.spelers)
        {
            personen.add(p.getGebruikersnaam());
        }
        for(Persoon p : this.spectators)
        {
            personen.add(p.getGebruikersnaam() + " (Spectator)");
        }
        for(Persoon p : this.spelers)
        {
            try {
                p.getClient().updateGameLobbyPlayers(personen);
            } catch (RemoteException ex) {
                Logger.getLogger(GameLobby.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for(Persoon p : this.spectators)
        {
            try {
                p.getClient().updateGameLobbyPlayers(personen);
            } catch (RemoteException ex) {
                Logger.getLogger(GameLobby.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public int getSpelerSize(Persoon p)
    {
        if(spelers.get(0) == p)
        {
             return spelers.size();
        }
        else
        {
            return 0;
        }
    }
    /**
     * game op starten nadat je op de knop start game drukt
     */
    public void startGame()
    {
        if(spelers.size() <= 3)
        {
            this.game = new Game();
         
            for(Persoon s : this.spelers)
            {
                Speler t = s.getSpeler(game);
                System.out.println("startGame in GameLobby voor Speler: "+ t.getGebruikersnaam());
                
                t.setPlayerNr(game.addSpeler(t));
                System.out.println("GameLobby speler toevoegen aan game: " + s.getGebruikersnaam());
                
                s.notifyGameStart(t);
            }
            
            for(Persoon s : this.spectators)
            {
                Toeschouwer t = s.getToeschouwer(game);
                System.out.println("startGame in GameLobby voor Toeschouwer: "+ t.getGebruikersnaam());
                
                game.addToeschouwer(t);
                
                s.notifyGameStart(t);
            }
            
            this.game.startGame();
            this.hasStarted = true;
        }
    }
    /**
     * verwijderen van speler uit lobby
     * @param p persoon
     */
    public void removeSpeler(Persoon p)
    {
        this.spelers.remove(p);
        this.spotsLeft++;
        
        p.leaveGameLobby();
        
        if(spelers.size() == 0)
        {
            p.removeLobby(this);
        }
    }
}
