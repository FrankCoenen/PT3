/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package proftaakpong;

import Domain.game.ChatBericht;
import Domain.game.GameEigenaar;
import Domain.game.GameLobby;
import Domain.game.Lobby;
import Domain.game.Persoon;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

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
    @FXML private ListView list_chatbox;
    @FXML private ChoiceBox cb_onbekend;
    @FXML private TextField tf_chatbericht;
    @FXML private Button btn_chatberichtsend;
    
    //LOBBY FXML
    @FXML private Label lbl_lobbyplayers;
    @FXML private ListView lv_lobbyplayers;
    @FXML private Label lbl_gameplayers;
    @FXML private ListView lv_gameplayers;
    @FXML private ListView lv_games;
    @FXML private Button btn_creategame;
    @FXML private Button btn_joingame;
    @FXML private Button btn_spectategame;
    @FXML private Text text_loggedin;
    
    @FXML private Button btn_startgame;
    
    private String username;
    
    private Lobby lobby;
    
    private Persoon persooninapplicatie;
    
    public void setData(Persoon p)
    {
        text_loggedin.setText("Logged in as: " + p.getGebruikersnaam());
        lbl_playername.setText(p.getGebruikersnaam());
        System.out.println(p.getGebruikersnaam());
        this.lobby = new Lobby(p);
        persooninapplicatie = p;
        btn_startgame.setVisible(false);  
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        
    }
    
    public void lobbyCreateGame(Event event) throws IOException
    {
        lobby.createGame();
    }
    
    public void lobbySpectageGame(Event event) throws IOException
    {
        
    }
    
    public void plaatsChatBericht(Event event)
    {
        String chatbericht = tf_chatbericht.getText();
        
        if(chatbericht.equals(""))
        {
            
        }
        else
        {
            this.lobby.createChatBericht(chatbericht, persooninapplicatie);
            System.out.println("ChatBericht Geplaatst in FXController");
            this.updateChatBox();
            System.out.println("ChatBox updated!");
            this.tf_chatbericht.clear();
        }
    }
    
    public void updateChatBox()
    {
        list_chatbox.setItems(this.lobby.getChatBox().getBerichten());
    }
}
