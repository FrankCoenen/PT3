/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;

import classes.Speelveld;
import classes.Speler;
import java.rmi.Remote;


/**
 *
 * @author Michael
 */
public interface IGame extends Remote
{
    IGame addAI();
    IGame resetBal();
    IGame startGame();
    IGame stopBatje();
    IGame moveBatjeRight();
    IGame moveBatjeLeft();
    String getHoogsteScore();
    Speler[] getSpelers();
    Speelveld getSpeelveld();
}
