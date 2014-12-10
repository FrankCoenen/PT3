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
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
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
    
    @FXML private Button btn_startgame;
    
    private Client client;
    
    public LobbyFXController()
    {
        try 
        {
            client = Client.getInstance(this);
            client.initializeLobbyChat();
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
    
    public void lobbyCreateGame(Event event) throws IOException
    {
        
    }
    
    public void lobbySpectageGame(Event event) throws IOException
    {
        
    }
    
    public void plaatsChatBericht(Event event)
    {
        client.sendChatBericht(tf_chatbericht.getText());
    }
    
    public void updateChatBox(ObservableList<ChatBericht> chatBerichten)
    {
        this.list_chatbox.setItems(chatBerichten);
    }
    public void updateLobbyPlayers(ObservableList<String> players)
    {
        this.lv_lobbyplayers.setItems(players);
    }
}
