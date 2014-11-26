/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proftaakpong;

import Domain.game.Persoon;
import Storage.game.DatabaseMediator;
import com.sun.prism.paint.Color;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.event.Event;
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

/**
 *
 * @author Michael
 */
public class LoginFXController implements Initializable {

    private DatabaseMediator mediator;
    private Parent root;
    private Scene scene;
    private Stage stage;
    private Scene currentscene;
    private Stage currentstage;

    //INLOGGUI FXML
    @FXML
    TextField tf_inlogusername;
    
    @FXML
    PasswordField pf_inlogpassword;
    
    @FXML Button btn_inlog;
    
    @FXML
    Button btn_register;
    
    @FXML
    Button btn_delete;
    
    @FXML
    Text text_melding;
    
    String username;

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        mediator = new DatabaseMediator();
    }

    public final void loginPersoon(Event event) throws IOException
    {
        String gebruikersnaam = tf_inlogusername.getText();
        String wachtwoord = pf_inlogpassword.getText();
        text_melding.setText("");
        
        if(!gebruikersnaam.equals("") || !wachtwoord.equals(""))
        {
            Persoon p = new Persoon(gebruikersnaam, wachtwoord);
        
            Boolean check = mediator.controleerPersoonsGegevens(gebruikersnaam, wachtwoord);

            if (check == false) 
            {
                text_melding.setText("Gebruikers gegeven komen niet voor in systeem!");
            } 
            else 
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
                
                ctrl1.setData(p);

                //show the stage
                stage.showAndWait();
            }
        }
        else
        {
            text_melding.setText("Niet alle verplichte velden zijn ingevuld!");
        }
    }

    public final void registerPersoon(Event event) 
    {
        String gebruikersnaam = tf_inlogusername.getText();
        String wachtwoord = pf_inlogpassword.getText();
        text_melding.setText("");


        if(!gebruikersnaam.equals("") || !wachtwoord.equals(""))
        {
            Persoon p = new Persoon(gebruikersnaam, wachtwoord);

            Boolean checkpersoon = mediator.controleerPersoonsGegevens(gebruikersnaam, wachtwoord);

            if (checkpersoon == false) 
            {
                mediator.registreerPersoonsGegevens(gebruikersnaam, wachtwoord);
                tf_inlogusername.clear();
                pf_inlogpassword.clear();
            } 
            else 
            {
                text_melding.setText("Persoon met deze gegevens bestaat al!");
            }
        }
        else
        {
            text_melding.setText("Vul alle verplichte velden in!");
        }
    }

    private Stage getStage() {
        return (Stage) tf_inlogusername.getScene().getWindow();
    }   

}
