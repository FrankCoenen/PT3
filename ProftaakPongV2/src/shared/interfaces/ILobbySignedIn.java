/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package shared.interfaces;

import shared.observer.RemotePublisher;

/**
 *
 * @author Merijn
 */
public interface ILobbySignedIn {
    public IGame StartGame(IClient client);
    public RemotePublisher getChatbox();
    public void sendChat(String bericht);
    public RemotePublisher CreateGame();
    public RemotePublisher JoinGame();
    public IGame spectateGame();
}
