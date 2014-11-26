/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;

import classes.Speelveld;
import classes.Speler;


/**
 *
 * @author Michael
 */
public interface IGame 
{
    void addAI();
    void resetBal();
    void startGame();
    void stopBatje();
    void moveBatjeRight();
    void moveBatjeLeft();
    String getHoogsteScore();
    Speler[] getSpelers();
    Speelveld getSpeelveld();
}
