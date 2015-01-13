/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server.components.game;

import server.components.Speler;
import java.io.Serializable;
import javafx.scene.paint.Color;

/**
 *
 * @author Merijn
 */
public class Batje implements Serializable
{
    private double radius;
    private double xPos;
    private double yPos;
    private transient double speed;
    private transient double direction;
    private transient double maxDistance;
    private transient double distanceMoved;
    private transient Speler speler;
    private transient Color color;
    private double hue;
    
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
        hue = color.getHue();
        
    }
    
    public Color getKleur()
    {
        return this.color;
    }
    
    public double getHue()
    {
        return hue;
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
