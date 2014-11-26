package Interfaces.game;


import Domain.game.Speler;
import Domain.game.Speler;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Michael
 */
public interface ILineGoal 
{
    double getX1Position();
    double getY1Position();
    double getX2Position();
    double getY2Position();
    void setSpeler(Speler speler);
    Speler getSpeler();
}
