/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server.components;

import server.components.game.Speelveld;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import shared.observer.RemotePublisher;
import shared.serializable.ChatBericht;

/**
 *
 * @author Michael
 */
public class Game extends TimerTask
{
    private ChatBox chatbox = null;
    private Speler[] spelers;
    private ArrayList<Toeschouwer> toeschouwers;
    private AI[] ai;
    private Timer gameTimer;
    private Speler speler;
    private Speler speler2;
    private Speler speler3;
    
    private Speelveld speelveld;
    
    public Game()
    {
        try {
            chatbox = new ChatBox();
        } catch (RemoteException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.spelers = new Speler[3];
       
        //KOMT ANDERE KEER EERST REST
        this.ai = new AI[3];
        
        this.toeschouwers = new ArrayList<Toeschouwer>();
        
        this.gameTimer = new Timer();    
    }
    
    public int addSpeler(Speler speler)
    {
        System.out.println("Game addSpeler: " + speler.getGebruikersnaam());
        for(int i = 0;i<spelers.length;i++)
        {
            if(spelers[i] == null)
            {
                spelers[i] = speler;
                System.out.println("Vanuit game Speler toevoegen aan game: " + speler.getGebruikersnaam());
                return i;
            }
        }
        return -1;
    }
    
    public void addToeschouwer(Toeschouwer toeschouwer)
    {
        toeschouwers.add(toeschouwer);
    }

    public void sendChat(ChatBericht bericht) throws RemoteException 
    {
        chatbox.addBericht(bericht);
    }

    public RemotePublisher getChatboxRemote() throws RemoteException 
    {
        return this.chatbox;
    }

    public ChatBox getChatbox()
    {
        return this.chatbox;
    }
    
    public void startGame()
    {
        boolean vol = true;
        
        for(int i = 0; i<spelers.length; i++)
        {
            if(spelers[i] == null)
            {
                vol = false;
            }
        }
        
        if(vol)
        {
            this.speelveld = new Speelveld(this.spelers);
        
            //GAMECLASS IMPLEMENTS TIMERTASK <-
            this.gameTimer.schedule(this,0,40); 
        }
    }
    
    public String getHoogsteScore()
    {
        Speler winner = null;
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

    @Override
    public void run() 
    {
        if(speelveld.getRound() <= 10)
        {
            speelveld.update();
        
            for(int i = 0; i<spelers.length; i++)
            {
                spelers[i].update();
                spelers[i].updateSpeelveld(speelveld);
            }

            for(Toeschouwer t : this.toeschouwers)
            {
                t.updateSpeelveld(speelveld);
            }
        }
        else{
            if(!speelveld.isWaiting())
            {
                for(Speler s : spelers){
                    if(speler == null)
                    {
                        speler = s;
                    }
                    else if( speler2 == null)
                    {
                        speler2 = s;
                    }
                    else if(speler3 == null)
                    {
                        speler3 = s;
                    }   
                }
                try {
                    speler.berekenEindScore(speler2.getScore(), speler3.getScore());
                    speler2.berekenEindScore(speler.getScore(), speler3.getScore());
                    speler3.berekenEindScore(speler.getScore(), speler2.getScore());
                    //shit voor update rating en score
                    //notify spelers en spectators game close
                    //kill objecten en shit in lobby
                    //nog andere shit misschien
                } catch (RemoteException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else
            {
                speelveld.update();
            }
        }
    }  
    
    public Speelveld getSpeelveld()
    {
        return this.speelveld;
    }   
    
    public Speler[] getSpelers()
    {
        return this.spelers;
    }
}