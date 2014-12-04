/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package proftaakpong;

import Domain.game.Speelveld;
import Domain.game.AI;
import Domain.game.Speler;
import Domain.game.Batje;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Merijn
 */
public class Game extends TimerTask {
    
    private Speelveld speelveld;
    private Speler[] spelers;
    private List<AI> ai;
    private Timer gameTimer;
    private boolean moveLeft;
    private boolean moveRight;
    
    public Game(Speler actieveSpeler)
    {
        this.spelers = new Speler[3];
        this.spelers[0] = actieveSpeler;
        this.gameTimer = new Timer();
        this.moveLeft = false;
        this.moveRight = false;
    }
    
    public Speelveld getSpeelveld()
    {
        return this.speelveld;
    }
        
    public Speler[] getSpelers()
    {
        return this.spelers;
    }
    
    public void resetBal()
    {
        this.speelveld.getBall().reset();
    }
    
    public void addAI()
    {
        this.ai = new ArrayList<AI>();
        if(spelers[1] == null)
        {
            AI ai1 = new AI("AI1", this.speelveld.getBall());
            spelers[1] = ai1;
            ai.add(ai1);
        }
        if(spelers[2] == null)
        {
            AI ai2 = new AI("AI2", this.speelveld.getBall());
            spelers[2] = ai2;
            ai.add(ai2);
        }
    }
    
    public void startGame()
    {
        this.speelveld = new Speelveld(this.spelers);
                
        //DEZE STOND EERST BOVEN DE SPEELVELD
        this.addAI();
        
        this.speelveld.generateBatjes(this.spelers);
        this.speelveld.generateLines(this.spelers);
        
        //GAMECLASS IMPLEMENTS TIMERTASK <-
        this.gameTimer.schedule(this,0,40); 
    }

    public void stopBatje() {
       this.moveLeft = false;
       this.moveRight = false;
    }

    public void moveBatjeRight() {
        this.moveRight = true;
    }

    public void moveBatjeLeft() {
        this.moveLeft = true;
    }

    @Override
    public void run() 
    {
        if(speelveld.getRound() == 10)
        {
            this.gameTimer.cancel();
            System.out.println("Game is over");
        }
        else
        {
            for (AI a : ai)
            {
                a.moveBatje();
            }     
            if (moveLeft)
            {
                spelers[0].getBatje().moveLeft();
            }
            if (moveRight)
            {
                spelers[0].getBatje().moveRight();
            }
            speelveld.update();
        } 
    }
    
    public String getHoogsteScore()
    {
        Speler winner = new Speler("BADEENDJE");
        int topScore = 0;
        
        for (Speler s : this.spelers)
        {
            if (s.getScore() > topScore)
            {
                winner = s;
                topScore = s.getScore();
            }
        }
        
        return winner.getGebruikersnaam();
    }
    
}
