/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package classes;

import database.DatabaseMediator;
import interfaces.IClient;
import interfaces.IGame;
import interfaces.IGameLobby;
import interfaces.ILobby;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 *
 * @author Michael
 */
public class Lobby extends UnicastRemoteObject implements ILobby
{
    private ChatBox chatbox;
    private ArrayList<Persoon> ingelogden;
    private Persoon persooninapplicatie;
    private DatabaseMediator database;
    private ArrayList<GameLobby> gamelobbies;
    private GameLobby joinedgamelobby;
    
    public Lobby(Persoon persooninapplicatie) throws RemoteException
    {
        this.persooninapplicatie = persooninapplicatie;
        gamelobbies = new ArrayList<>();
        this.chatbox = new ChatBox();
    }
    
    public Lobby() throws RemoteException
    {
        
    }
    
    @Override
    public IGameLobby getActiveGameLobby()
    {
        return (IGameLobby)this.joinedgamelobby;
    }
    
    @Override
    public DatabaseMediator getDatabase()
    {
        return this.database;
    }
    
    @Override
    public void setDatabase(DatabaseMediator database)
    {
        this.database = database;
    }
    
    @Override
    public IGame createGame()
    {
        GameLobby newgame = new GameLobby(persooninapplicatie.getGebruikersnaam() + " :Game", persooninapplicatie.omzettenSpeler().omzettenSpelEigenaar());
        gamelobbies.add(newgame);
        this.joinGame(newgame);
        
        IGame game = (IGame)newgame.getGame();
        
        return game;
    }
    
    @Override
    public IGame startGame()
    {
        IGame game = (IGame)joinedgamelobby.getGame();
        joinedgamelobby.startGame();
        return game;
    }
    
    @Override
    public IGame joinGame(GameLobby gamelobby)
    {
        joinedgamelobby = gamelobby;
        IGame game = (IGame)joinedgamelobby.getGame();
        
        return game;
    }
    
    @Override
    public IGame spectateGame(GameLobby gamelobby)
    {
        joinedgamelobby = gamelobby;
        IGame game = (IGame)joinedgamelobby.getGame();
        Toeschouwer spectator = persooninapplicatie.omzettenToeschouwer();
        
        return game;
    }
    
    @Override
    public IClient inloggen()
    {
        return null;
    }
    
    @Override
    public void createChatBericht(String text, Persoon p)
    {
        ChatBericht bericht = new ChatBericht(text, p);
        System.out.println("ChatBericht Object Aangemaakt met Text: " + bericht.getTekst());
        this.chatbox.addBericht(bericht);
        System.out.println("ChatBericht toegevoegd aan ChatBox Observable List");
        System.out.println("List Count: " + this.chatbox.getListCount());
    }
    
    @Override
    public ChatBox getChatBox()
    {
        return this.chatbox;
    }
    
    @Override
    public IClient setPersoonInApplicatie(Persoon p)
    {
        return null;
    }
}
