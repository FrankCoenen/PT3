/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package proftaakpong;

import Domain.game.Bal;
import Domain.game.Batje;
import Domain.game.LineGoal;
import Domain.game.LineSide;
import Domain.game.Speler;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
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
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Merijn
 */
public class GameController implements Initializable {
    
    @FXML
    Canvas canvas;
    
    private Game game;
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
    private TextArea chatarea;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        start();
        Speler speler = new Speler("WOESTE BADEEND");
        game = new Game(speler);
        game.startGame();
        canvasNew.setHeight(hoogte);
        gc = canvasNew.getGraphicsContext2D();
        AnimationTimer at = new AnimationTimer() 
        {
            @Override
            public void handle(long now) 
            {
                //TODO draw here
                gc.setFill(Color.BLACK);
                gc.fillRect(0, 0, canvasNew.getHeight(), canvasNew.getHeight());

                for (LineSide l : game.getSpeelveld().getLines()) 
                {
                    gc.setStroke(Color.WHITE);
                    gc.strokeLine(l.getX1Position() * hoogte, l.getY1Position() * hoogte, l.getX2Position() * hoogte, l.getY2Position() * hoogte);
                }
                for (Batje b : game.getSpeelveld().getBatjes()) {
                    if (b.getKleur() == Color.RED) 
                    {
                        gc.setFill(Color.DARKRED);
                    } 
                    else if (b.getKleur() == Color.GREEN) 
                    {
                        gc.setFill(Color.DARKGREEN);
                    } 
                    else 
                    {
                        gc.setFill(Color.DARKBLUE);
                    }

                    gc.fillOval((b.getXPos() - (b.getDiameter())) * hoogte, (b.getYPos() - (b.getDiameter())) * hoogte, b.getDiameter()* 2 * hoogte, b.getDiameter() * 2 * hoogte);
                }
                for (LineGoal l : game.getSpeelveld().getGoals()) 
                {
                    gc.setStroke(Color.CRIMSON);
                    gc.strokeLine(l.getX1Position() * hoogte, l.getY1Position() * hoogte, l.getX2Position() * hoogte, l.getY2Position() * hoogte);
                }

                Bal b = game.getSpeelveld().getBall();
                gc.setFill(Color.WHITE);
                gc.fillOval((b.getXPos() - (b.getDiameter())) * hoogte, (b.getYPos() - (b.getDiameter())) * hoogte, b.getDiameter() * 2 * hoogte, b.getDiameter() * 2 * hoogte);
                
                speler1scoreLabel.setText("Score : " + game.getSpelers()[0].getGebruikersnaam() + " " + game.getSpelers()[0].getScore());
                speler2scoreLabel.setText("Score : " + game.getSpelers()[1].getGebruikersnaam() + " " + game.getSpelers()[1].getScore());
                speler3scoreLabel.setText("Score : " + game.getSpelers()[2].getGebruikersnaam() + " " + game.getSpelers()[2].getScore());
                roundLabel.setText("Round: " + game.getSpeelveld().getRound());
                
                if(game.getSpeelveld().getRound() == 10)
                {
                    roundLabel.setText("Round: Winner is " + game.getHoogsteScore());
                }
            }
                        
            @Override
            public void stop()
            {
                super.stop();
            } 
        };
        
        at.start();
        
    }
    
    private void initialiseerKeyEvents(Stage stage) 
    {
        EventHandler<KeyEvent> keyPressed = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) 
            {
                if (e.getCode() == KeyCode.A || e.getCode() == KeyCode.LEFT) 
                {
                    game.moveBatjeLeft();
                    System.out.println("<-- Batje naar Links");
                } 
                else if (e.getCode() == KeyCode.D || e.getCode() == KeyCode.RIGHT) 
                {
                    game.moveBatjeRight();
                    System.out.println("Batje naar Rechts -->");
                }
                else if (e.getCode() == KeyCode.MINUS)
                {
                    game.resetBal();
                    System.out.println("BAL RESET!");
                }
            }
        };
        
        EventHandler<KeyEvent> keyReleased = new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent event) 
            {
                game.stopBatje(); 
                System.out.println("Batje stoppen!");
            }  
        };
               
        stage.addEventFilter(KeyEvent.KEY_PRESSED, keyPressed);
        stage.addEventFilter(KeyEvent.KEY_RELEASED, keyReleased);
    }
    
            public void start()
            {
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                this.hoogte = (int)screenSize.getHeight();
                System.out.println("Start");
                BorderPane grid = new BorderPane();
               
                Group group = new Group();
                group.getChildren().add(grid);
                
                canvasNew = new Canvas(hoogte - 50, hoogte - 50);
                grid.setCenter(canvasNew);
                grid.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
              
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
                Scene scene = new Scene(group,screenSize.getWidth(),screenSize.getHeight());
                
                stage = new Stage();
                stage.setTitle("GameGUI");
                stage.setScene(scene);
                stage.setFullScreen(true);
                stage.show();
                initialiseerKeyEvents(stage);
                
                System.out.println("Tekenen Succesvol");
                
            }
    
}
