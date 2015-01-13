/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.gui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Merijn
 */
public class ClientStart extends Application 
{
    
    @Override
    public void start(Stage stage) throws IOException 
    {
        Parent root = FXMLLoader.load(getClass().getResource("LoginGUI.fxml"));
        
        LoginFXController fx = new LoginFXController();
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
                
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
