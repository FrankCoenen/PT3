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
 * Deze class is voor als je bent ingelogt. dus als je je in de hoofd-lobby
 * bevindt. Hier staan de methodes in van welke dingen je in de hoofd-lobby 
 * kan doen zoals de game starten etc. Deze class is een Singleton.
 * @author Merijn
 */
public interface ILobbySignedIn extends Remote {
    
    /**
     * Met deze mothode start je de game zodra er 3 mensen in de gamelobby bevind.
     * Deze methode kan alleen worden aangeroepen door de gameowner
     * @param client
     * @return een IGame
     * @throws RemoteException omdat deze class remote extends
     */
    public void StartGame() throws RemoteException;
    
    /**
     * Met deze methode haal je de Chatbox op zodat de mensen
     * berichten kunnen lezen van andere mensen.
     * @return een RemotePublisher
     * @throws RemoteException omdat deze class remote extends
     */
    public RemotePublisher getChatboxLobby() throws RemoteException;
    
    /**
     * Met deze methode kan je een chatbericht verzenden die wordt naar de chatbox gestuurd
     * elke gebruiker kan deze methode gebruiken als je ingelogd bent.
     * @param bericht is het bericht(String) wat de gebruiker heeft verstuurd.
     * @throws RemoteException omdat deze class remote extends.
     */
    public void sendChatLobby(String bericht) throws RemoteException;
    
    /**
     * Met deze methode kan elke ingelogd gebruiker een game aan maken als je nog
     * niet in een game zit en nog niet in een lobby zit.
     * @return een RemotePublsiher
     * @throws RemoteException omdat deze class remote extends.
     */
    public boolean CreateGame() throws RemoteException;
    
    /**
     * Met deze methode kan je een lobby joinen die nog geen 3 personen bevat.
     * Dus kan je een lobby joinen die door iemand anders al is gemaakt.
     * @return een RemotePublisher
     * @throws RemoteException omdat deze class remote extends
     */
    public void JoinGame(String gamename) throws RemoteException;
    
    /**
     * Met deze methode kan je een game spectaten die al bestaat en die al bezig is.
     * Dit kan je alleen doen als je in de lobby zit en aan nog geen gamelobby 
     * verbonden is
     * @return een IGame
     * @throws RemoteException omdat deze class remote extends
     */
    public void spectateGame(String gamename) throws RemoteException;
    
    /**
     * 
     * @throws RemoteException 
     */
    public void logOut() throws RemoteException;
    
    /**
     * 
     * @return
     * @throws RemoteException 
     */
    public String showGebruikersNaam() throws RemoteException;
    
    public int getSpelerSize() throws RemoteException;

}
