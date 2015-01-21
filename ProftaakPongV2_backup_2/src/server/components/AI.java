/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server.components;

import java.rmi.RemoteException;
import javafx.scene.paint.Color;
import server.components.game.Bal;
import server.components.game.Speelveld;
import shared.interfaces.IClient;

/**
 *
 * @author Michael
 */
public class AI extends Speler
{
    
    private final Bal bal;

    public AI(IClient client, String naam, Lobby lobby, Game game, Bal bal) throws RemoteException {
        super(client, naam, lobby, game);
        this.bal = bal;
    }
    
    @Override
    public void update() 
    {
        if(batje.getKleur() == Color.RED)
        {
            if (this.bal.getXPos() > batje.getXPos())
            {
                batje.moveRight();
            }
            else if(this.bal.getXPos() < batje.getXPos())
            {
                batje.moveLeft();
            }
        }
        else if (batje.getKleur() == Color.BLUE)
        {
            if (this.bal.getYPos() > batje.getYPos())
            {
                batje.moveRight();
            }
            else if(this.bal.getYPos() < batje.getYPos())
            {
                batje.moveLeft();
            }
        }
        else if (batje.getKleur() == Color.GREEN)
        {
            if (this.bal.getYPos() > batje.getYPos())
            {
                batje.moveLeft();
            }
            else if(this.bal.getYPos() < batje.getYPos())
            {
                batje.moveRight();
            }
        }  
    }
    
    @Override
    public void updateSpeelveld(Speelveld sv)
    {
        // niet sturen
    }
    //SHIT
}
