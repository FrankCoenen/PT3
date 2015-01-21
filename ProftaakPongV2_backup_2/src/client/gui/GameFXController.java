/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.gui;

import client.components.Client;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import server.components.game.Bal;
import server.components.game.Batje;
import server.components.game.LineGoal;
import server.components.game.LineSide;
import server.components.game.Speelveld;
import shared.serializable.ChatBericht;

/**
 *
 * @author Merijn
 */
public class GameFXController implements Initializable {

    private @FXML
    Canvas canvas;

    private Canvas canvasNew;
    private GraphicsContext gc;
    private Stage stage;
    private int hoogte;
    private final int ronde = 0;
    private Text speler1scoreLabel;
    private Text speler2scoreLabel;
    private Text speler3scoreLabel;
    private Text timerlabel;
    private Text roundLabel;

    /**
     * TEXT AREA GAME
     */
    private Label lbl_playername;
    private Label lbl_chat;
    private ListView<ChatBericht> list_chatbox;
    private TextField tf_chatbericht;

    private Client client;

    /**
     * constructor
     */
    public GameFXController() {
        try {
            client = Client.getInstance(this);
            client.initializeGameChat();
        } catch (RemoteException ex) {
            Logger.getLogger(LoginFXController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * update de tekstbox waarin alle chatberichten staat als er een nieuw bericht bijkomt
     * @param chatBerichten
     */
    public void updateChatBox(ObservableList<ChatBericht> chatBerichten) 
    {
        this.list_chatbox.setItems(chatBerichten);
    }

    /**
     * Plaatst een chatbericht wat je net hebt getypt en op verzenden hebt gedrukt
     */
    public void plaatsChatBericht()
    {
        client.sendChatBerichtGame(tf_chatbericht.getText());
        tf_chatbericht.setText("");
    }

    /**
     * set de playername
     * @param names de namen van alle spelers
     */
    public void setPlayerNames(String[] names) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //start();
        //Speler speler = new Speler("WOESTE BADEEND");
        //game = new Game(speler);
        //game.startGame();
        canvasNew.setHeight(hoogte);
        gc = canvasNew.getGraphicsContext2D();
        AnimationTimer at = new AnimationTimer() {
            @Override
            public void handle(long now) {
                //TODO draw here

//                speler1scoreLabel.setText("Score : " + game.getSpelers()[0].getGebruikersnaam() + " " + game.getSpelers()[0].getScore());
//                speler2scoreLabel.setText("Score : " + game.getSpelers()[1].getGebruikersnaam() + " " + game.getSpelers()[1].getScore());
//                speler3scoreLabel.setText("Score : " + game.getSpelers()[2].getGebruikersnaam() + " " + game.getSpelers()[2].getScore());
//                roundLabel.setText("Round: " + game.getSpeelveld().getRound());
//                
//                if(game.getSpeelveld().getRound() == 10)
//                {
//                    roundLabel.setText("Round: Winner is " + game.getHoogsteScore());
//                }
            }

            @Override
            public void stop() {
                super.stop();
            }
        };

        at.start();

    }
/**
 * initaliseert de keyevents
 * @param stage het scherm
 */
    private void initialiseerKeyEvents(Stage stage) 
    {

        EventHandler<KeyEvent> keyPressed = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) 
            {
                if (e.getCode() == KeyCode.A || e.getCode() == KeyCode.LEFT) {
                    client.moveLeft();
                    
                } else if (e.getCode() == KeyCode.D || e.getCode() == KeyCode.RIGHT) {
                    client.moveRight();
                    
                } 
                
            }
        };
//        
        EventHandler<KeyEvent> keyReleased = new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent event) 
            {
                if(event.getCode() == KeyCode.D || event.getCode() == KeyCode.LEFT ||  event.getCode() == KeyCode.A ||  event.getCode() == KeyCode.RIGHT)
                {
                    client.stopMove();
                }
                else if ( event.getCode() == KeyCode.ENTER && tf_chatbericht.getText().trim().length() > 1)
                {
                     plaatsChatBericht();
                }
            }  
        };
//               
        stage.addEventFilter(KeyEvent.KEY_PRESSED, keyPressed);
        stage.addEventFilter(KeyEvent.KEY_RELEASED, keyReleased);
    }

    /**
     * Maakt de GUi
     */
    public void start() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.hoogte = (int) screenSize.getHeight();
        System.out.println("Start");
        BorderPane grid = new BorderPane();

        Group group = new Group();
        group.getChildren().add(grid);

        canvasNew = new Canvas(hoogte - 50, hoogte - 50);
        canvasNew.setHeight(hoogte);
        gc = canvasNew.getGraphicsContext2D();
        grid.setCenter(canvasNew);
        grid.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        tf_chatbericht = new TextField();
        list_chatbox = new ListView<ChatBericht>();

        speler1scoreLabel = new Text("Speler 1: ");
        speler1scoreLabel.setFill(Color.RED);
        speler1scoreLabel.setFont(new Font(25));
        speler2scoreLabel = new Text("Speler 2: ");
        speler2scoreLabel.setFill(Color.BLUE);
        speler2scoreLabel.setFont(new Font(25));
        speler3scoreLabel = new Text("Speler 3: ");
        speler3scoreLabel.setFill(Color.GREEN);
        speler3scoreLabel.setFont(new Font(25));
        roundLabel = new Text("Round: ");
        roundLabel.setFont(new Font(25));
        roundLabel.setFill(Color.CORAL);
        timerlabel = new Text("3");
        timerlabel.setFont(new Font(20));
        timerlabel.setVisible(false);
        SplitPane scorePane = new SplitPane();
        scorePane.setOrientation(Orientation.HORIZONTAL);
        scorePane.getItems().add(speler1scoreLabel);
        scorePane.getItems().add(speler2scoreLabel);
        scorePane.getItems().add(speler3scoreLabel);
        scorePane.getItems().add(roundLabel);
        scorePane.getItems().add(timerlabel);
        scorePane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        grid.setTop(scorePane);
        grid.setMinWidth(screenSize.width);
        grid.setMinHeight(screenSize.height);
        Scene scene = new Scene(group, screenSize.getWidth(), screenSize.getHeight());

        scorePane.getItems().add(tf_chatbericht);
        scorePane.getItems().add(list_chatbox);
        list_chatbox.setMaxHeight(100);
        
        //speler1scoreLabel.setText("Score : " + game.getSpelers()[0].getGebruikersnaam() + " " + game.getSpelers()[0].getScore());
        //speler2scoreLabel.setText("Score : " + game.getSpelers()[1].getGebruikersnaam() + " " + game.getSpelers()[1].getScore());
        //speler3scoreLabel.setText("Score : " + game.getSpelers()[2].getGebruikersnaam() + " " + game.getSpelers()[2].getScore());
        
        
        canvasNew.setRotate(client.getRotation());
        System.out.println("Rotation: " + client.getRotation());
        
        stage = new Stage();
        stage.setTitle("GameGUI");
        stage.setScene(scene);
        //stage.setFullScreen(true);
        stage.show();
        initialiseerKeyEvents(stage);
        

        System.out.println("Tekenen Succesvol");
    }

    /**
     * tekent het speelveld wat de spelers te zien krijgen in-game.
     * @param sv het speelveld
     * @param sides de zijkanten van het speelveld
     * @param goals de goals in het speelveld
     */
    public void drawSpeelveld(Speelveld sv, LineSide[] sides, LineGoal[] goals) 
    {
        this.roundLabel.setText(Integer.toString(sv.getRound()));
        
        if(sv.getRound() == 10)
        {
            try 
            {
                Thread.sleep(1000);
            } 
            catch (InterruptedException ex) 
            {
                Logger.getLogger(GameFXController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            // get a handle to the stage
            Stage stage = (Stage) roundLabel.getScene().getWindow();
            // do what you have to do
            stage.close();
            client.requestClose("game");
        }
        else
        {
                    gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvasNew.getHeight(), canvasNew.getHeight());

        for (LineSide l : sides) 
        {
            gc.setStroke(Color.WHITE);
            gc.strokeLine(l.getX1Position() * hoogte, l.getY1Position() * hoogte, l.getX2Position() * hoogte, l.getY2Position() * hoogte);
        }
        for (Batje b : sv.getBatjes()) 
        {
            if (b.getHue() == Color.RED.getHue()) 
            {
                gc.setFill(Color.DARKRED);
            } 
            else if (b.getHue() == Color.GREEN.getHue()) 
            {
                gc.setFill(Color.DARKGREEN);
            } 
            else 
            {
                gc.setFill(Color.DARKBLUE);
            }

            gc.fillOval((b.getXPos() - (b.getDiameter())) * hoogte, (b.getYPos() - (b.getDiameter())) * hoogte, b.getDiameter() * 2 * hoogte, b.getDiameter() * 2 * hoogte);
        }
        for (LineGoal l : goals) {
            gc.setStroke(Color.CRIMSON);
            gc.strokeLine(l.getX1Position() * hoogte, l.getY1Position() * hoogte, l.getX2Position() * hoogte, l.getY2Position() * hoogte);
        }

        Bal b = sv.getBall();
        gc.setFill(Color.WHITE);
        gc.fillOval((b.getXPos() - (b.getDiameter())) * hoogte, (b.getYPos() - (b.getDiameter())) * hoogte, b.getDiameter() * 2 * hoogte, b.getDiameter() * 2 * hoogte);
        }

    }

}
