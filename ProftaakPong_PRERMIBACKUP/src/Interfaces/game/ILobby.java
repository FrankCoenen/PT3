/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interfaces.game;

import Domain.game.ChatBox;
import Domain.game.GameLobby;
import Domain.game.Persoon;
import Storage.game.DatabaseMediator;

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
