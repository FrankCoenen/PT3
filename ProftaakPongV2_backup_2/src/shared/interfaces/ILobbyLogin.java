/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package shared.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Merijn
 */
public interface ILobbyLogin extends Remote
{
    /**
     * Met deze methode wordt je vanuit het startscherm geplaatst in het programma zelf dus de lobby.
     * dit gebeurt alleen maar als de login succesvol is.
     * @param client Hij krijgt een client mee die probeert in te loggen
     * @param naam De naam van de speler
     * @param wachtwoord Het wachtwoord van de speler.
     * @return een Ilobbysignedin Deze krijg je als de speler succesvol is aangemeld
     * @throws RemoteException een exception voor als er iets fout gaat met het remote deel.
     */
    public ILobbySignedIn login(IClient client, String naam, String wachtwoord) throws RemoteException;
    /**
     * Met deze methode kan een nieuwe gebruiker registeren. Hiervoor moet de username uniek zijn. Doordat
     * je registreert in het programma kan je inloggen. Deze gegevens worden opgeslagen in de database. Als je 
     * geen account hebt dan kan je ook het spel niet spelen.
     * @param naam De usernaam die de gebruiker heeft ingevult. deze moet uniek zijn.
     * @param wachtwoord Het wachtwoord wat de gebruiker heeft ingevult. 
     * @return een boolean als deze true returnt dan is het gelukt  returnt deze false dan is het mislukt en 
     * moet je het opnieuw doen.
     * @throws RemoteException een exception voor als er iets fout gaat met het remote deel.
     */
    public boolean register(String naam, String wachtwoord) throws RemoteException; 
    /**
     * Deze methode geeft de nieuwe speler een standaard rating van 15. Na het spelen van elk
     * potje wordt er een nieuwe score ingezet.
     * @param Gebruikersnaam de gebruikersnaam van de speler
     * @param Score de score die automatisch wordt meegeven
     * @return een boolean voor als het niet gelukt is dat is het false.
     * @throws RemoteException 
     */
    public boolean nieuweScore(String Gebruikersnaam, int Score) throws RemoteException;
}
