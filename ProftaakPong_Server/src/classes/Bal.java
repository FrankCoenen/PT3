/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package classes;

/**
 *
 * @author Merijn
 */
public class Bal {
    
    private double startXpos;
    private double startYpos;   
    
    private double radius;
    private double xPos;
    private double yPos;
    private double speed;
    private double direction;
    private Batje batjeLast;
    private Speler latstHit;
    
    public Bal(double radius, double xPos, double yPos, double speed, double direction)
    {
        this.radius = radius;
        this.xPos = xPos;
        this.yPos = yPos;
        this.speed = speed;
        this.direction = direction;
        
        this.startXpos = xPos;
        this.startYpos = yPos;
    }
    
    public double getXPos()
    {
        return this.xPos;
    }
    
    public double getYPos()
    {
        return this.yPos;
    }
    
    public double getDiameter()
    {
        return this.radius;
    }
    
    
    public void move()
    {
       this.xPos += this.speed * Math.cos(Math.toRadians(direction));
       this.yPos += this.speed * Math.sin(Math.toRadians(direction));
    }
    
    public void reset()
    {
        this.xPos = this.startXpos;
        this.yPos = this.startYpos;
        this.direction = Math.random() * 360;
        this.latstHit = null;
    }

    public void setDirection(double newDirection) {
        this.direction = newDirection;
    }

    public double getDirection() {
        return this.direction;
    }

    public double getXPosition() {
        return this.xPos;
    }

    public double getRadius() {
        return this.radius;
    }

    public double getYPosition() {
        return this.yPos;
    }
    
    public void setLastHit(Speler speler){
        this.latstHit = speler;
    }
    
    public Speler getLastHit()
    {
        return this.latstHit;
    }

    void setLastBatje(Batje b) {
        this.batjeLast = b;
    }
    public Batje getLastBatje()
    {
        return this.batjeLast;
    }
}
