/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.components;

import client.communications.ChatBoxListener;
import client.gui.GameFXController;
import client.gui.LeaderboardFXController;
import client.gui.LobbyFXController;
import client.gui.LoginFXController;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import server.components.game.LineGoal;
import server.components.game.LineSide;
import server.components.game.Speelveld;
import shared.interfaces.IClient;
import shared.interfaces.IGame;
import shared.interfaces.ILobbyLogin;
import shared.interfaces.ILobbySignedIn;
import shared.serializable.ChatBericht;



/**
 *
 * @author Michael
 */
public class Client extends UnicastRemoteObject implements IClient
{
    private static LoginFXController loginFXController;
    private static LobbyFXController lobbyFXController;
    private static GameFXController gameFXController;
    private static LeaderboardFXController leaderboardFXController;
    
    private static ChatBoxListener lobbyChatListener;
    private static ChatBoxListener gameChatListener;
    
    private static Client INSTANCE;
    
    private static IGame game;
    private static ILobbySignedIn lobby;
    private static ILobbyLogin login;
    
    private static Registry registry;
    
    private static ObservableList playerList;
    private static ObservableList gameLobbyList;
    private static ObservableList gameLobbyPlayerList;
    
    private static boolean leftMove;
    private static boolean rightMove;
    
    private static LineSide[] lines;
    private static LineGoal[] goals;
    
    private static boolean gameFX = false;
    private static boolean lobbyFX = false;
    private static boolean loginFX = false;
                    
    
    private Client() throws RemoteException
    {
        try
        {
            registry = LocateRegistry.getRegistry("145.144.243.150", 1099);
        }
        catch(RemoteException e)
        {
                     
        }
        try
        {
            login = (ILobbyLogin)registry.lookup("lobby");
        }
        catch(NotBoundException | RemoteException e)
        {
                         
        }
    }
    
    public static Client getInstance(LoginFXController controller) throws RemoteException
    {
        if(INSTANCE == null)
        {
            INSTANCE = new Client();
        }
        loginFXController = controller;
        loginFX = true;
        return INSTANCE;
    }
    
    public static Client getInstance(LeaderboardFXController controller) throws RemoteException
    {
        if(INSTANCE == null)
        {
            INSTANCE = new Client();
        }
        leaderboardFXController = controller;
        return INSTANCE;
    }
    
    public static Client getInstance(LobbyFXController controller) throws RemoteException
    {
        if(INSTANCE == null)
        {
            INSTANCE = new Client();
        }
        lobbyFXController = controller;
        getGebruikersNaam();
        updatePlayerListGui();
        updateGameLobbyListGUI();
        clientInLobby();
        lobbyFX = true;
        return INSTANCE;
    }
    
    public static Client getInstance(GameFXController controller) throws RemoteException
    {
        if(INSTANCE == null)
        {
            INSTANCE = new Client();
        }
        gameFXController = controller;
        leftMove = false;
        rightMove = false;
        gameFX = true;
        return INSTANCE;
    }
    
    public void logIn(String gebruikersnaam, String wachtwoord)
    {
        try 
        {
            lobby = login.login(this, gebruikersnaam, wachtwoord);
            
            if(lobby == null)
            {
                System.out.println("foute inloggegevens");
            }
            else
            {
                openLobbyGUI();
            }
        } 
        catch (RemoteException ex) 
        {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void register(String gebruikersnaam, String wachtwoord)
    {
        try
        {
           login.register(gebruikersnaam, wachtwoord);
           System.out.println("Register Goed");
        }
        catch(Exception e)
        {
            System.out.println("Register Fout");
        }
    }
    
    public void openLobbyGUI()
    {
        System.out.println("Probeert LobbyGUI te openen Platform.runLater");
        Platform.runLater(new Runnable(){

            @Override
            public void run() 
            {
                loginFXController.openLobbyGUI();
            }
        });
    }
    
    public void updateLobbyChatBox(ObservableList<ChatBericht> list_cb)
    {
        System.out.println("Platform.runLater observable list adden");

        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                lobbyFXController.updateChatBox(list_cb);
            }
        });
    }
    
    /**
     * Methode voor het updaten van de chatbox in de GameFXController
     * @param list_cb 
     */
    public void updateGameChatBox(ObservableList<ChatBericht> list_cb)
    {
        System.out.println("Platform.runLater observable list adden");

        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                gameFXController.updateChatBox(list_cb);
            }
        });
    }
    
    public void sendChatBericht(String bericht)
    {
        try {
            lobby.sendChatLobby(bericht);
        } catch (RemoteException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * TOEGEVOEGD NAAR KIJKEN!!
     * @param bericht 
     */
    public void sendChatBerichtGame(String bericht)
    {
        try {
            game.sendChat(bericht);
        } catch (RemoteException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void initializeLobbyChat()
    {
        try {
            lobbyChatListener = new ChatBoxListener(INSTANCE, lobby.getChatboxLobby(),"lobby");
        } catch (RemoteException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * TOEGEVOEGD NAAR KIJKEN!!
     */
    public void initializeGameChat()
    {
        try {
            gameChatListener = new ChatBoxListener(INSTANCE, game.getChatboxRemote(), "game");
        } 
        catch (RemoteException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updatePlayerList(List<String> spelers) throws RemoteException {
        playerList = FXCollections.observableArrayList(spelers);
        
        if(lobbyFXController != null)
        {
            this.updatePlayerListGui();
        }
        
    }
    
    public static void updatePlayerListGui()
    {
        Platform.runLater(new Runnable(){

            @Override
            public void run() {
                lobbyFXController.updateLobbyPlayers(playerList);
            }
        });
    }

    @Override
    public boolean ping() throws RemoteException 
    {
        return true;
    }

    @Override
    public void updateGameLobbyList(List<String> gameLobbys) throws RemoteException {
        gameLobbyList = FXCollections.observableArrayList(gameLobbys);
        
        if(lobbyFXController != null)
        {
            this.updateGameLobbyListGUI();
        }
    }

    /**
     * Word gebruikt om de list aan te passen van de GUI van GameLobby
     * aanwezige spelers
     * @param gameLobbySpelers
     * @throws RemoteException 
     */
    @Override
    public void updateGameLobbyPlayers(List<String> gameLobbySpelers) throws RemoteException 
    {
        gameLobbyPlayerList = FXCollections.observableArrayList(gameLobbySpelers);
        
        this.getSpelerSize();
        if(lobbyFXController != null)
        {
            this.updateGameLobbyListPlayersGUI();
        }
    }

    private static void updateGameLobbyListGUI() 
    {
        Platform.runLater(new Runnable(){

            @Override
            public void run() 
            {
                lobbyFXController.updateGameLobbys(gameLobbyList);
            }
        });    
    }

    private static void updateGameLobbyListPlayersGUI() 
    {
        Platform.runLater(new Runnable()
        {

            @Override
            public void run() 
            {
                lobbyFXController.updateGameLobbyPlayers(gameLobbyPlayerList);
            }
        });    
    }
    
    public void createGameLobby()
    {
        try 
        {
            lobby.CreateGame();
            clientInLobby();
        } 
        catch (RemoteException ex) 
        {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void joinGameLobby(String gamename)
    {
        try {
            lobby.JoinGame(gamename);
            clientInLobby();
        } catch (RemoteException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void spectateGameLobby(String gamename)
    {
        try {
            lobby.spectateGame(gamename);
            clientInLobby();
        } catch (RemoteException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void getGebruikersNaam()
    {
        Platform.runLater(new Runnable() 
        {

            @Override
            public void run() {
                try {
            lobbyFXController.setPlayerNameLobby(lobby.showGebruikersNaam());
        } 
                catch (RemoteException ex) 
        {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
            }
        });
    }
    
    private int getSpelerSize()
    {
        try 
        {
            if(lobby.getSpelerSize() < 3)
            {
                Platform.runLater(new Runnable(){

                    @Override
                    public void run() {
                        lobbyFXController.disableStartGameButton();
                    }
                });
            }
            else
            {
                Platform.runLater(new Runnable(){

                    @Override
                    public void run() {
                        lobbyFXController.enableStartGameButton();
                    }
                });
            }
        } 
        catch (RemoteException ex) 
        {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 0;
    }
    
    public void startGame()
    {
        try {
            lobby.StartGame();
        } catch (RemoteException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void startGameClient(IGame game) throws RemoteException {
        this.game = game;
        
        Platform.runLater(new Runnable(){

                    @Override
                    public void run() {
                        lobbyFXController.openGameGUI();
                    }
                });
        
    }
    
    @Override
    public void updateSpeelveld(Speelveld speelveld)
    {
        Platform.runLater(new Runnable(){

                    @Override
                    public void run() {
                        gameFXController.drawSpeelveld(speelveld, lines, goals);
                    }
                });
    }
    
    public double getRotation()
    {
        double position = 0;
        try {
            position = game.rotatePosition();
        } catch (RemoteException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (position == 0)
        {
            return 0;
        }
        else if (position == 1)
        {
            return 240;
        }
        else if (position == 2)
        {
            return 120;
        }
        
        
        position = -999;
        return position;
    }
    public void moveLeft()
    {
        try {
            
            if (!leftMove)
            {
                game.setMoveLeft(true);
                leftMove = true;
                System.out.println("left");
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void moveRight()
    {
        try {
            if (!rightMove)
            {
                game.setMoveRight(true);
                rightMove = true;
                System.out.println("right");
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void stopMove()
    {
        try {
            game.setMoveLeft(false);
            game.setMoveRight(false);
            rightMove = false;
            leftMove = false;
            System.out.println("stop");
        }
        catch(RemoteException ex)
        {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<String[]> toonLeaderboard()
    {
        ArrayList<String[]> returnvalue = new ArrayList<String[]>();
        try{
        String eigenNaam = lobby.showGebruikersNaam();
        String eigenScore = Double.toString(lobby.getRating(eigenNaam));
        String[] array = new String[2];
        array[0] = eigenNaam;
        array[1] = eigenScore;
        
        returnvalue.add(array);
        
        for(String[] s : lobby.getLeaderbord(lobby.showGebruikersNaam()))
        {
        returnvalue.add(s);
        }
        }
        catch(RemoteException e){
            
        }
        return returnvalue;
    }

    @Override
    public void setLines(LineSide[] newlines, LineGoal[] newgoals) throws RemoteException {
        lines = newlines;
        goals = newgoals;
    }
    
    public void requestClose(String controler)
    {
        if (controler.equals("login"))
        {
            loginFX = false;
            loginFXController = null;
        }
        else if(controler.equals("game"))
        {
            gameFX = false;
            gameFXController = null;
        }
        else if(controler.equals("lobby"))
        {
            lobbyFX = false;
            lobbyFXController = null;
        }
        
        if(!loginFX && !gameFX && !lobbyFX)
        {
            try {
                UnicastRemoteObject.unexportObject(this, true);
            } catch (NoSuchObjectException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    public void clientLeaveLobby()
    {
        try {
            lobby.leaveLobby(); 
            clientInLobby();
        } catch (RemoteException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void clientInLobby()
    {
        try {
            if(lobby.inLobby())
            {
                Platform.runLater(new Runnable(){

                    @Override
                    public void run() {
                          lobbyFXController.enableLeaveGameButton();
                    }
                });
            }
            else
            {
                Platform.runLater(new Runnable(){

                    @Override
                    public void run() {
                          lobbyFXController.disableLeaveGameButton();
                    }
                });
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}