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
            for(Speler s : spelers)
            {
                s.sendLines(speelveld.getLines(), speelveld.getGoals());
            }
        
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
                try {
                    spelers[i].update();
                    spelers[i].updateSpeelveld(speelveld);
                } catch (RemoteException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                    spelers[i] = spelers[i].becomeRobot();
                }
            }

            for(Toeschouwer t : this.toeschouwers)
            {
                t.updateSpeelveld(speelveld);
            }
        }
        else{
            if(!speelveld.isWaiting())
            {
                System.out.println("Server Game in Waiting attempting ScoreShit");
                
                try {
                    int rating1 = (int)spelers[0].getRating(spelers[0].getGebruikersnaam());
                    int rating2 = (int)spelers[1].getRating(spelers[1].getGebruikersnaam());
                    int rating3 = (int)spelers[2].getRating(spelers[2].getGebruikersnaam());
                    spelers[0].berekenEindScore(rating2, rating3);
                    spelers[1].berekenEindScore(rating1, rating3);
                    spelers[2].berekenEindScore(rating1, rating3);
                    //shit voor update rating en score
                    //notify spelers en spectators game close
                    //kill objecten en shit in lobby
                    //nog andere shit misschien
                    gameTimer.cancel();
                    gameTimer.purge();
                    gameTimer = null;
                    
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
