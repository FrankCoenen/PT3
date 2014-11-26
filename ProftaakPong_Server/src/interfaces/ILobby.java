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

/**
 *
 * @author Michael
 */
public interface ILobby 
{
    GameLobby getActiveGameLobby();
    DatabaseMediator getDatabase();
    void setDatabase(DatabaseMediator database);
    void createGame();
    void startGame();
    void joinGame(GameLobby gamelobby);
    void spectateGame();
    boolean inloggen();
    void createChatBericht(String text, Persoon p);
    ChatBox getChatBox();
}