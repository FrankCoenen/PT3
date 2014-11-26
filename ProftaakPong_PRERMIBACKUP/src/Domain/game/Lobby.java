/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Domain.game;

import Storage.game.DatabaseMediator;
import java.util.ArrayList;

/**
 *
 * @author Michael
 */
public class Lobby 
{
    private ChatBox chatbox;
    private ArrayList<Persoon> ingelogden;
    private Persoon persooninapplicatie;
    private DatabaseMediator database;
    private ArrayList<GameLobby> gamelobbies;
    private GameLobby joinedgamelobby;
    
    public Lobby(Persoon persooninapplicatie)
    {
        this.persooninapplicatie = persooninapplicatie;
        gamelobbies = new ArrayList<>();
        this.chatbox = new ChatBox();
    }
    
    public Lobby()
    {
        
    }
    
    public GameLobby getActiveGameLobby()
    {
        return this.joinedgamelobby;
    }
    
    public DatabaseMediator getDatabase()
    {
        return this.database;
    }
    
    public void setDatabase(DatabaseMediator database)
    {
        this.database = database;
    }
    
    public void createGame()
    {
        GameLobby newgame = new GameLobby(persooninapplicatie.getGebruikersnaam() + " :Game", persooninapplicatie.omzettenSpeler().omzettenSpelEigenaar());
        gamelobbies.add(newgame);
        this.joinGame(newgame);
    }
    
    public void startGame()
    {
        joinedgamelobby.startGame();
    }
    
    public void joinGame(GameLobby gamelobby)
    {
        joinedgamelobby = gamelobby;
    }
    
    public void spectateGame()
    {
        Toeschouwer spectater = persooninapplicatie.omzettenToeschouwer();
    }
    
    public boolean inloggen()
    {
        return false;
    }
    
    public void createChatBericht(String text, Persoon p)
    {
        ChatBericht bericht = new ChatBericht(text, p);
        System.out.println("ChatBericht Object Aangemaakt met Text: " + bericht.getTekst());
        this.chatbox.addBericht(bericht);
        System.out.println("ChatBericht toegevoegd aan ChatBox Observable List");
        System.out.println("List Count: " + this.chatbox.getListCount());
    }
    
    public ChatBox getChatBox()
    {
        return this.chatbox;
    }
}
