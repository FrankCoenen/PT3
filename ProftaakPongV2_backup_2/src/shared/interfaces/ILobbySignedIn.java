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
     * door deze methode kan je uitloggen hiermee wordt je uit de spelerslijst verwijderd.
     * @throws RemoteException een exception voor als er iets fout gaat met het remote deel.
     */
    public void logOut() throws RemoteException;
    
    /**
     * Met deze methode laten we de gebruikersnaam zien in de lobby
     * @return een string met de gebruikersnaam.
     * @throws RemoteException  een exception voor als er iets fout gaat met het remote deel.
     */
    public String showGebruikersNaam() throws RemoteException;
    /**
     * Met deze methode halen we op hoeveel spelers er in de lobby zitten
     * @return een int met een aantal spelers.
     * @throws RemoteException een exception voor als er iets fout gaat met het remote deel.
     */
    public int getSpelerSize() throws RemoteException;
    /**
     * Met deze methode halen we de rating op van een persoon. Dit doen we zodra de speler inlogt.
     * en ook als een game is afgelopen want dan is de rating van een speler veranderd
     * @param Gebruikersnaam de naam van de speler
     * @return een double van wat de rating van de persoon is
     * @throws RemoteException  een exception voor als er iets fout gaat met het remote deel.
     */
    public double getRating(String Gebruikersnaam) throws RemoteException;
}
