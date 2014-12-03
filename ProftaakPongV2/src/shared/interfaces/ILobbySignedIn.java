/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package shared.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import shared.observer.RemotePublisher;

/**
 *
 * @author Merijn
 */
public interface ILobbySignedIn extends Remote {
    public IGame StartGame(IClient client) throws RemoteException;
    public RemotePublisher getChatbox() throws RemoteException;
    public void sendChat(String bericht) throws RemoteException;
    public RemotePublisher CreateGame() throws RemoteException;
    public RemotePublisher JoinGame() throws RemoteException;
    public IGame spectateGame() throws RemoteException;
}
