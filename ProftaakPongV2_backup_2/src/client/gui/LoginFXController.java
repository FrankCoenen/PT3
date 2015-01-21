/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.gui;

import client.components.Client;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import shared.interfaces.IClient;

/**
 *
 * @author Michael
 */
public class LoginFXController implements Initializable 
{
    //INLOGGUI FXML
    @FXML private
    TextField tf_inlogusername;
    
    @FXML private
    PasswordField pf_inlogpassword;
    
    @FXML private Button btn_inlog;
    
    @FXML private Button btn_register;
    
    @FXML private Button btn_delete;
    
    @FXML private Text text_melding;
    
    private Parent root;
    private Scene scene;
    private Stage stage;
    private Scene currentscene;
    private Stage currentstage;
    
    private Client client;

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        try 
        {
            client = Client.getInstance(this);
        } 
        catch (RemoteException ex) 
        {
            Logger.getLogger(LoginFXController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public final void loginPersoon(Event event)
    {
        String username = tf_inlogusername.getText();
        String password = pf_inlogpassword.getText();
        
        client.logIn(username, password);
    }

    public void registerPersoon(Event event) throws IOException 
    {
        String username = tf_inlogusername.getText();
        String password = pf_inlogpassword.getText();
        
        client.register(username, password);
    }
    
    public void openLobbyGUI() 
    {
        try
        {
            URL location1 = LobbyFXController.class.getResource("LobbyGUI.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(location1);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            
            root = (Parent)(Node)fxmlLoader.load(location1.openStream());

            LobbyFXController ctrl1 = (LobbyFXController) fxmlLoader.getController();
            
            stage = new Stage();
            scene = new Scene(root);
            stage.setScene(scene);
                     
            //show the stage
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

                @Override
                public void handle(WindowEvent event) {
                    client.requestClose("lobby");
                    Stage stage2 = (Stage) tf_inlogusername.getScene().getWindow();
                    stage2.close(); 
                    
                }
            });
            Stage stage2 = (Stage) tf_inlogusername.getScene().getWindow();
            stage2.hide();           
        }
        catch(IOException e)
        {
            System.out.println("loggin fx fout" + e.getMessage());
            System.out.println(e.getMessage());
            e.getMessage();
        }
    }

}
