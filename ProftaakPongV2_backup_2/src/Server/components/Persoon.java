/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server.components;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.components.Game;
import server.components.Speler;
import server.components.game.Speelveld;
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
    /**
     * variabelen met een logische naam
     */
    protected transient IClient client;
    private String naam;
    protected transient Lobby lobby;
    private transient GameLobby gameLobby;
    
    /**
     * constuctor
     * @param client de client van de gebruiker
     * @param naam de naam van de speler
     * @param lobby lobby
     * @throws RemoteException 
     */
    public Persoon(IClient client, String naam, Lobby lobby) throws RemoteException
    {
        this.client = client;
        this.naam = naam;
        this.lobby = lobby;
    }
    

    @Override
    public void StartGame() 
    {
        if (this.gameLobby != null)
        {
            gameLobby.startGame();
        }
    }

    @Override
    public RemotePublisher getChatboxLobby() 
    {
        return (RemotePublisher)lobby.getChatBox();
    }
    
    @Override
    public void sendChatLobby(String bericht) 
    {
        lobby.getChatBox().addBericht(new ChatBericht(bericht,(Persoon)this));
    }
    
    @Override
    public boolean CreateGame() 
    {
        if(this.inLobby())
        {
            return false;
        }
        else
        {
            this.gameLobby = lobby.createGame(this);
            return true;
        }
    }

    @Override
    public void JoinGame(String gamename) 
    {
        if(this.inLobby())
        {
            this.leaveLobby();
        }
        
        for(GameLobby l : this.lobby.getGames())
        {
            if(l.getName().equals(gamename))
            {
                l.addSpelers(this);
                this.gameLobby = l;
            }
        }
    }

    @Override
    public void spectateGame(String gamename) 
    {
        for(GameLobby l : this.lobby.getGames())
        {
            if(l.getName().equals(gamename))
            {
                l.addSpectators(this);
                this.gameLobby = l;
            }
        }
    }

    public String getGebruikersnaam() 
    {
        return this.naam;
    }
    
    public IClient getClient()
    {
        return client;
    }

    @Override
    public void logOut() throws RemoteException 
    {
        lobby.getPersonen().remove(this);
        lobby.updatePersonen();
        
        //Om het object daadwerkelijk te verwijderen anders blijft hij bestaan
        //word niet opgehaald door garbage collector!
        UnicastRemoteObject.unexportObject(this, true);
    }

    @Override
    public String showGebruikersNaam() throws RemoteException 
    {
        return this.getGebruikersnaam();
    }
    
    public Speler getSpeler(Game game)
    {
        try 
        {
            return new Speler(this.client, this.naam, this.lobby, game);
        } 
        catch (RemoteException ex) 
        {
            Logger.getLogger(Persoon.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public Toeschouwer getToeschouwer(Game game)
    {
        try 
        {
            return new Toeschouwer(this.client, this.naam, this.lobby, game);
        } 
        catch (RemoteException ex) 
        {
            Logger.getLogger(Persoon.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    @Override
    public int getSpelerSize()
    {
        if(this.gameLobby != null)
        {
            return this.gameLobby.getSpelerSize(this);
        }
        else
        {
            return 0;
        }
    }
    
    /**
     * een notify voor als de game gestart is naar de andere clients
     * @param game de game
     */
    public void notifyGameStart(IGame game)
    {
        try {
            this.client.startGameClient(game);
        } catch (RemoteException ex) {
            Logger.getLogger(Persoon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * het type dat de persoon op dit moment is
     * @return 
     */
    public String getType()
    {
        return "persoon";
    }

    @Override
    public double getRating(String Gebruikersnaam) throws RemoteException {
        return lobby.getRating(Gebruikersnaam);
    }
    
    //RETURN TRUE ALS HIJ IN LOBBY ZIT
    @Override
    public boolean inLobby()
    {
        if(this.gameLobby == null)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
        
    @Override
    public void leaveLobby()
    {
        String gamelobby = this.gameLobby.getName();

        this.gameLobby.removeSpeler(this);
        this.gameLobby.updateGUI();
        this.gameLobby = null;
        
        System.out.println(this.getGebruikersnaam() + ": " + "Removed from GameLobby: " + gamelobby);
    }

    @Override
    public ArrayList<String[]> getLeaderbord(String showGebruikersNaam) throws RemoteException 
    {
        return this.lobby.getLeaderbord(showGebruikersNaam);
    }

    @Override
    public void removeLobby(GameLobby aThis) 
    {
        lobby.removeLobby(aThis);
    }
    
    public void leaveGameLobby()
    {
        this.gameLobby = null;
    }
    
}
