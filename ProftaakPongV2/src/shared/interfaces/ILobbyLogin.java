/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package shared.interfaces;

/**
 *
 * @author Merijn
 */
public interface ILobbyLogin {
    public ILobbySignedIn login(IClient client, String naam, String wachtwoord);
    
}
