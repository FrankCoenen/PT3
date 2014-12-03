/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package classes;

/**
 *
 * @author Michael
 * In deze klasse worden de objecten en attributen geregeld die gebruikt worden tussen Game en zijn componenten.
 */
public class GameManager 
{
    private Game managedgame;
    
    public GameManager()
    {
        
    }
    
    public double getBatje1XPos()
    {
        double XPos = this.managedgame.getSpeelveld().getBatjes()[0].getXPos();

        return XPos;
    }
    
    public double getBatje1YPos()
    {
        double YPos = this.managedgame.getSpeelveld().getBatjes()[0].getYPos();
        
        return YPos;
    }
    
    public double getBatje2XPos()
    {
        double XPos = this.managedgame.getSpeelveld().getBatjes()[1].getXPos();

        return XPos;
    }
    
    public double getBatje2YPos()
    {
        double YPos = this.managedgame.getSpeelveld().getBatjes()[1].getYPos();
        
        return YPos;
    }
    
    public double getBatje3XPos()
    {
        double XPos = this.managedgame.getSpeelveld().getBatjes()[2].getXPos();

        return XPos;
    }
    
    public double getBatje3YPos()
    {
        double YPos = this.managedgame.getSpeelveld().getBatjes()[2].getYPos();
        
        return YPos;
    }
    
    public double getBalYPos()
    {
        double YPos = this.managedgame.getSpeelveld().getBall().getYPos();
        
        return YPos;
    }
    
    public double getBalXPos()
    {
        double XPos = this.managedgame.getSpeelveld().getBall().getXPos();
        
        return XPos;
    }
    
    public double getBalDirection()
    {
        double direction = this.managedgame.getSpeelveld().getBall().getDirection();
        
        return direction;
    }
    
    public double getScorePlayer1()
    {
        double score = this.managedgame.getSpelers()[0].getScore();
        
        return score;
    }
    
    public double getScorePlayer2()
    {
        double score = this.managedgame.getSpelers()[1].getScore();
        
        return score;
    }
    
    public double getScorePlayer3()
    {
        double score = this.managedgame.getSpelers()[2].getScore();
        
        return score;
    }
    
    public String getNamePlayer1()
    {
        String naam = this.managedgame.getSpelers()[0].getGebruikersnaam();
        
        return naam;
    }
    
    public String getNamePlayer2()
    {
        String naam = this.managedgame.getSpelers()[1].getGebruikersnaam();
        
        return naam;
    }
    
    public String getNamePlayer3()
    {
        String naam = this.managedgame.getSpelers()[2].getGebruikersnaam();
        
        return naam;
    }
}
