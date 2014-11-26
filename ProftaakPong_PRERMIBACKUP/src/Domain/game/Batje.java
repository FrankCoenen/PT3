/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Domain.game;

import Domain.game.Speler;
import javafx.scene.paint.Color;

/**
 *
 * @author Merijn
 */
public class Batje {
    private double radius;
    private double xPos;
    private double yPos;
    private double speed;
    private double direction;
    private double maxDistance;
    private double distanceMoved;
    private Speler speler;
    private Color color;
    
    public Batje(double radius, double xPos, double yPos, double speed, double direction, double maxDistance, double distanceMoved, Speler speler, Color color)
    {
        this.radius = radius;
        this.xPos = xPos;
        this.yPos = yPos;
        this.speed = speed;
        this.direction = direction;
        this.speler = speler;
        this.color = color;
        this.maxDistance = maxDistance;
        this.distanceMoved = distanceMoved;
    }
    
    public Color getKleur()
    {
        return this.color;
    }

    public void moveLeft() {
        if (distanceMoved > -maxDistance) 
        {
            this.xPos += this.speed * Math.cos(Math.toRadians(direction - 180));
            this.yPos += this.speed * Math.sin(Math.toRadians(direction - 180));
            this.distanceMoved -= speed;
        }
    }

    public void moveRight() {
        if (distanceMoved < maxDistance)
        {
            this.xPos += this.speed * Math.cos(Math.toRadians(direction));
            this.yPos += this.speed * Math.sin(Math.toRadians(direction));
            this.distanceMoved += speed;
        }
    }

    public double getYPos() {
        return this.yPos;
    }

    public double getXPos() {
        return this.xPos;
    }

    public double getDiameter() {
        return this.radius;
    }
    
    public Speler getSpeler() {
        return this.speler;
    }
    
    
}
