/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.gui;

import client.components.Client;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import shared.interfaces.IClient;
import shared.serializable.ChatBericht;

/**
 *
 * @author Michael
 */
public class LobbyFXController implements Initializable {
    
    //private Lobby lobby;
        
    //MENU FXML
    @FXML private MenuBar Lobby_Menubar;
    @FXML private Menu menu_credits;
    @FXML private Menu menu_game;
    @FXML private Menu menu_help;
    @FXML private Menu menu_options;
    @FXML private MenuItem menuitem_loguit;
    @FXML private MenuItem menuitem_exit;
    @FXML private MenuItem menuitem_graphics;
    @FXML private MenuItem menuitem_controls;
    @FXML private MenuItem menuitem_creategame;
    @FXML private MenuItem menuitem_joingame;
    @FXML private MenuItem menuitem_spectategame;
    @FXML private MenuItem menuitem_credits;
    @FXML private MenuItem menuitem_copyright;
    
    @FXML private MenuItem menuitem_leadboards;
    
    //CHATFIELD FXML
    @FXML private Label lbl_playername; 
    @FXML private Label lbl_chat;
    @FXML private ListView<ChatBericht> list_chatbox;
    @FXML private ChoiceBox cb_onbekend;
    @FXML private TextField tf_chatbericht;
    @FXML private Button btn_chatberichtsend;
    
    //LOBBY FXML
    @FXML private Label lbl_lobbyplayers;
    @FXML private ListView<String> lv_lobbyplayers;
    @FXML private Label lbl_gameplayers;
    @FXML private ListView lv_gameplayers;
    @FXML private ListView lv_games;
    @FXML private Button btn_creategame;
    @FXML private Button btn_joingame;
    @FXML private Button btn_spectategame;
    @FXML private Text text_loggedin;
    
    @FXML private Button btn_logout;
    
    @FXML private Button btn_startgame;
    
    private Parent root;
    private Scene scene;
    private Stage stage;
    private Scene currentscene;
    private Stage currentstage;
       
    private Client client;
    
    /**
     * de constuctor.
     */
    public LobbyFXController()
    {
        try 
        {
            client = Client.getInstance(this);
            client.initializeLobbyChat();
            client.requestClose("login");
        } 
        catch (RemoteException ex) 
        {
            Logger.getLogger(LoginFXController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {        
        
    }
    /**
     * laat de spelersnaam zien in de lobby.
     * @param text de naam van de speler.
     */
    public void setPlayerNameLobby(String text)
    {
        this.lbl_playername.setText(text);
    }
    
    /**
     * Met deze methode wordt een gamelobby aangemaakt die mensen kunnen joinen zitten er 3 spelers in 
     * dan kan je het spel starten
     * @param event de knop create game
     * @throws IOException 
     */
    public void lobbyCreateGame(Event event) throws IOException
    {
        client.createGameLobby();
    }
    
    /**
     * door deze methode kan je een aangemaakte lobby al joinen dit kan je doen door uit de listview
     * op de desbetreffende game te drukken.
     * @param event als je op de game drukt wordt de methode aangeroepen.
     * @throws IOException 
     */
    public void lobbyJoinGame(Event event) throws IOException
    {
        if(this.lv_games.getSelectionModel().getSelectedItem() == null)
        {
            
        }
        else
        {
            String gamename = (String)this.lv_games.getSelectionModel().getSelectedItem();
            client.joinGameLobby(gamename);
        }
    }
    /**
     * door deze methode kan je een lobby gaan spectaten die al bestaad.
     * @param event door op de knop spectate te drukken
     * @throws IOException 
     */
    public void lobbySpectateGame(Event event) throws IOException
    {
        if(this.lv_games.getSelectionModel().getSelectedItem() == null)
        {
            
        }
        else
        {
            String gamename = (String)this.lv_games.getSelectionModel().getSelectedItem();
            client.spectateGameLobby(gamename);
        }
    }
    /**
     * als je een chatbericht wilt plaatsen
     * @param event  de knop plaats bericht als je erop drukt wordt je bericht geplaatst.
     */
    public void plaatsChatBericht(Event event)
    {
        client.sendChatBericht(tf_chatbericht.getText());
    }
    /**
     * Als er een nieuw bericht wordt geplaatst wordt de lijst van chatberichten opgedate zodat
     * de nieuwe berichten ook voor iedereen te zien zijn
     * @param chatBerichten  de lijst van alle chatberichten
     */
    public void updateChatBox(ObservableList<ChatBericht> chatBerichten)
    {
        this.list_chatbox.setItems(chatBerichten);
    }
    /**
     * update de lijst van alle lobby spelers. Dit gebeurt er als iemand nieuws joint
     * in de lobby.
     * @param players de lijst met spelers die in de lobby zitten
     */
    public void updateLobbyPlayers(ObservableList<String> players)
    {
        this.lv_lobbyplayers.setItems(players);
    }
    /**
    * update de gamelobbylijst voor als er een nieuwe gamelobby bijkomt.
    * @param playerList 
    */
    public void updateGameLobbys(ObservableList playerList) 
    {
        this.lv_games.setItems(playerList);
    }
    /**
     * update de lijst van de spelers die in de lobby zitten deze wordt aangeroepen
     * als er iemand joint.
     * @param gameLobbyPlayerList  de lijst met spelers in de gamelobby
     */
    public void updateGameLobbyPlayers(ObservableList gameLobbyPlayerList) 
    {
        this.lv_gameplayers.setItems(gameLobbyPlayerList);
    }
    
    /**
     * uitloggen
     */
    public void logUit()
    {
        
    }
    /**
     * zorg je ervoor dat je de game nog niet kan starten als er nog geen 3 spelers in zitten
     */
    public void disableStartGameButton()
    {
        this.btn_startgame.setDisable(true);
    }
    /**
     * zodra er 3 spelers zijn kan je de game pas starten
     */
    public void enableStartGameButton()
    {
        this.btn_startgame.setDisable(false);
    }
    /**
     * opent de gameGUI
     */
    public void openGameGUI()
    {
        GameFXController nieuw = new GameFXController();
        nieuw.start();
    }
    /**
     * Start de game als je op de startgame knop drukt.
     * @param event de knop als je drukt van start game
     */    
    public void StartGame(ActionEvent event)
    {
        this.client.startGame();
    }
    /**
     * open de leaderboardGUI
     * @param event als je drukt op de knop openleaderboard wordt deze GUI geopent.
     */
    public void openLeadboardGUI(ActionEvent event)
    {
        URL location1 = LeaderboardFXController.class.getResource("LeaderboardGUI.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location1);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        try {
            root = (Parent)(Node)fxmlLoader.load(location1.openStream());
        } catch (IOException ex) {
            Logger.getLogger(LobbyFXController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }

        LeaderboardFXController ctrl1 = (LeaderboardFXController) fxmlLoader.getController();  

        stage = new Stage();
        scene = new Scene(root);
        stage.setScene(scene);
                
        //show the stage
        stage.showAndWait();
    }
}
