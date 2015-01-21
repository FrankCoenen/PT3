/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server.components.game;

import java.io.Serializable;


/**
 *
 * @author Merijn
 */
public class LineSide implements Serializable
{
    /**
     * variabelen voor de coordinaten.
     */
    private double x1;
    private double y1;
    private double x2;
    private double y2;
    
    /**
     * constructor
     * @param x1 coordinaten
     * @param y1 coordinaten
     * @param x2 coordinaten
     * @param y2 coordinaten
     */
    public LineSide(double x1, double y1, double x2, double y2)
    {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    
    public double getX1Position() {
        return this.x1;
    }

    
    public double getY1Position() {
        return this.y1;
    }

    
    public double getX2Position() {
        return this.x2;
    }

    
    public double getY2Position() {
        return this.y2;
    }
    
}
