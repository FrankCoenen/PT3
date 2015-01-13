/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Server.components;

import Server.components.game.Speelveld;
import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.components.ChatBox;
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
    private AI[] ai;
    private Timer gameTimer;
    
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
        
        this.gameTimer = new Timer();    
    }
    
    public void addSpeler(Speler speler)
    {
        for(int i = 0;i<spelers.length;i++)
        {
            if(spelers[i] == null)
            {
                spelers[i] = speler;
            }
        }
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
        
        for(int i = 0;1<spelers.length;i++)
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
        speelveld.update();
        
        for(int i = 0;1<spelers.length;i++)
        {
            spelers[i].update();
        }
    }  
    
    public Speelveld getSpeelveld()
    {
        return this.speelveld;
    }   
}
