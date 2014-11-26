/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Domain.game;

/**
 *
 * @author Merijn
 */
public class Speler extends Persoon{

    private int score;
    private Batje batje;
    
    public Speler(String gebruikersNaam)
    {
        super(gebruikersNaam);
        score = 20;
    }
    
    public Batje getBatje() 
    {
        return this.batje;
    } 
    
    public void setBatje(Batje batje)
    {
        this.batje = batje;
    }
    
    public int getScore()
    {
        return this.score;
    }
    
    //Unittest
    public void setScore(int score)
    {
        this.score = score;
    }
        
    public GameEigenaar omzettenSpelEigenaar()
    {
        GameEigenaar nieuw = new GameEigenaar(this.gebruikersNaam);
        return nieuw;
    }
    
    public void scorePlus()
    {
        this.score++;
    }
    
    public void scoreMin()
    {
        this.score--;
    }
}
