/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server.components;

import Server.sql.BerekenRatingOp;
import Server.sql.HaalRatingOp;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.sql.controleerPersoonsGegevens;
import server.sql.registreerPersoonsGegevens;
import shared.interfaces.IClient;
import shared.interfaces.ILobbyLogin;
import shared.interfaces.ILobbySignedIn;
import shared.observer.RemotePublisher;
import shared.serializable.ChatBericht;




/**
 *
 * @author Michael
 */
public class Lobby extends UnicastRemoteObject implements ILobbyLogin
{
    private static Lobby INSTANCE; 
    private ChatBox chatbox;
    private List<Persoon> personen;
    private Timer ping;
    private List<GameLobby> games;
    
    private Lobby() throws RemoteException
    {
        personen = new ArrayList<Persoon>();
        games = new ArrayList<GameLobby>();
        chatbox = new ChatBox();
        ping = new Timer();
        TimerTask tTask = new TimerTask(){

            @Override
            public void run() {
                for(Persoon p : personen)
                {
                    try{
                        p.getClient().ping();
                    }
                    catch(Exception e)
                    {
                        personen.remove(p);
                        updatePersonen();
                    }
                }
            }
        };
        
        ping.schedule(tTask, 0, 1000);
    }
    
    public static Lobby getInstance() throws RemoteException
    {
        if(INSTANCE == null)
        {
            INSTANCE = new Lobby();
        }
        
        return INSTANCE;
    }

    @Override
    public ILobbySignedIn login(IClient client, String naam, String wachtwoord)
    {
        Boolean result = false;
        controleerPersoonsGegevens login = new controleerPersoonsGegevens(naam, wachtwoord);
        ExecutorService task = Executors.newSingleThreadExecutor();
        Future<Boolean> future = task.submit(login);
        try 
        {
             result = future.get();
        } 
        catch (InterruptedException ex) 
        {
            Logger.getLogger(Lobby.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (ExecutionException ex) 
        {
            Logger.getLogger(Lobby.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        task.shutdown();
        
        if(result)
        {
            Persoon p = null;
            try {
                p = new Persoon(client,naam,this);
            } catch (RemoteException ex) {
                Logger.getLogger(Lobby.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                return p;
            } finally {
                this.addPersoon(p);
            }
            
        }
        else
        {
            return null;
        }
    }

    @Override
    public boolean register(String naam, String wachtwoord) 
    {
        registreerPersoonsGegevens register = new registreerPersoonsGegevens(naam, wachtwoord);
        Thread t = new Thread(register);
        
        try
        {
            t.start();
        }
        catch(Exception E)
        {
            return false;   
        }
        finally
        {
               
        }
        return true;
    }
    
    private synchronized void addPersoon(Persoon p)
    {
        if(!personen.contains(p))
        {
            personen.add(p);
        }
        updatePersonen();
        updateGameLobbys();
    }
    
    //Hier nog even naar kijken
    public synchronized void addChatBericht(ChatBericht c)
    {
       this.chatbox.addBericht(c);
    }
    
    public RemotePublisher getChatBoxRemote()
    {
        return this.chatbox;
    }
    
    public synchronized ChatBox getChatBox()
    {
        return this.chatbox;
    }

    public void updatePersonen()
    {
        List<String> nameList = new ArrayList();
        
        for (Persoon pp : personen)
        {
            nameList.add(pp.getGebruikersnaam());
        }
        
        for(Persoon pp : personen)
        {
            IClient client = pp.getClient();
            try 
            {
                client.updatePlayerList(nameList);
            } 
            catch (RemoteException ex) 
            {
                Logger.getLogger(Lobby.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void updateGameLobbys()
    {
        List<String> gameList = new ArrayList();
        
        for (GameLobby gl : games)
        {
            gameList.add(gl.getName());
        }
        for (Persoon p : personen)
        {
            try 
            {
                p.getClient().updateGameLobbyList(gameList);
            } 
            catch (RemoteException ex) 
            {
                Logger.getLogger(Lobby.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public GameLobby createGame(Persoon p)
    {
        GameLobby gl = new GameLobby(p);
        games.add(gl);
        updateGameLobbys();
        return gl;
    }
    
    public List<Persoon> getPersonen() {
        return personen;
    }

    public void setPersonen(List<Persoon> personen) 
    {
        this.personen = personen;
    }
    
    
    public List<GameLobby> getGames() {
        return games;
    }

    public void setGames(List<GameLobby> games) {
        this.games = games;
    }
    public Boolean nieuweScore(String Gebruikersnaam, int Score) throws RemoteException
    {
        try{
        BerekenRatingOp bro = new BerekenRatingOp(Gebruikersnaam,Score);
        ExecutorService task = Executors.newSingleThreadExecutor();
        Future<Boolean> future = task.submit(bro);
        return future.get();
        }
        catch(InterruptedException  | ExecutionException e)
        {
            return false;
        }
        
    }

    public double getRating(String Gebruikersnaam) throws RemoteException {
        try {
            HaalRatingOp haalOp = new HaalRatingOp(Gebruikersnaam);
            ExecutorService task = Executors.newSingleThreadExecutor();
            Future<Double> future = task.submit(haalOp);
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            
        } finally {
            return 99.9;
        }
    }
    
    
} 
