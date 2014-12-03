/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;

import classes.ChatBox;
import classes.GameLobby;
import classes.Persoon;
import database.DatabaseMediator;
import java.rmi.Remote;

/**
 *
 * @author Michael
 */
public interface ILobby extends Remote
{
    IGameLobby getActiveGameLobby();
    DatabaseMediator getDatabase();
    void setDatabase(DatabaseMediator database);
    
    //Create gamelobby object
    IGame createGame();
    IGame startGame();
    IGame joinGame(GameLobby gamelobby);
    IGame spectateGame(GameLobby gamelobby);
    IClient inloggen();
    void createChatBericht(String text, Persoon p);
    ChatBox getChatBox();
    IClient setPersoonInApplicatie(Persoon p);
}
