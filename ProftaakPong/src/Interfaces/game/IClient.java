/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interfaces.game;

/**
 *
 * @author Michael
 * Interface voor IClient die de client voorsteld en zo methodes vanuit ILobby aanroept
 */
public interface IClient 
{
    /**
    * @param client
    * Het client object wat meegestuurd word voor de login
    * @param gebruikersnaam
    * Gebruikersnaam van de in te loggen persoon
    * @param wachtwoord
    * Wachtwoord van de in te loggen persoon
    * @return
    * ILobby object
    */
    ILobby logIn(IClient client, String gebruikersnaam, String wachtwoord);
    
    
}
