/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server.components.game;


import server.components.Speler;
import java.io.Serializable;
import static java.lang.Math.sin;
import static java.lang.Math.tan;
import javafx.scene.paint.Color;

/**
 *
 * @author Merijn
 */
public class Speelveld implements Serializable{
    
    /**
     * variabelen met een logische naam
     */
    private transient LineSide[] sides;
    private transient LineGoal[] goals;
    private Batje[] batjes;
    private Bal bal;
    private int round;
    private transient static final double lengte = 0.7;
    private transient boolean waiting;
    private int waitTime;
    
    /**
     * kjken of het speelveld wacht
     * @return een true als die wacht anders false
     */
    public boolean isWaiting()
    {
        return this.waiting;
    }
    /**
     * constructor
     * @param spelers lijst met spelers
     */
    public Speelveld(Speler[] spelers)
    {     
        this.sides = new LineSide[6];
        this.goals = new LineGoal[3];
        this.batjes = new Batje[3];
        this.round = 0;
        this.waiting = false;
                
        this.generateBal();
        this.generateBatjes(spelers);
        this.generateLines(spelers);
    }
    
    public LineSide[] getLines()
    {
        return this.sides;
    }
    
    public LineGoal[] getGoals()
    {
        return this.goals;
    }
    
    public Batje[] getBatjes()
    {
        return this.batjes;
    }
    
    public Bal getBall()
    {
        return this.bal;
    }
        
    /**
     * methode voor het maken van de lijnen
     * @param spelers  een array met spelers
     */
    public void generateLines(Speler[] spelers)
    {
        double hoogte = ((lengte/2) * tan(Math.PI/3));
        
        double bottomXl = (1 - lengte) / 2;
        double bottomXr = bottomXl + lengte;
        //LINKER HOEK - HELFT LENGTE
        double topX = bottomXl + (lengte / 2);
        //HAALT HOOGTE OP VAN DRIEHOEK ONDERLIJN TOT BOVENKANT
        //HELFT VAN DE TOTALE LENGTE - DE HELFT VAN DE TOTALE LENGTE
        //HELFT VAN DE LENGTE VAN HET MIDDEN VAN DE DRIEHOEK
        //PLUS DE HOOGTE VAN EEN KLEIN RESTANT WAT OVERGEHOUDEN WORD
        double topY = ((1 - hoogte) / 2) - (0.5 - (((0.5 - (hoogte / 2)) + ((lengte/2) * tan((Math.PI/6))))));
        //RELATIEF AAN DE HOOGTE VAN topY
        double bottomYl = (topY) + hoogte;
        double bottomYr = bottomYl;
        
        //40% -> BEIDE KANTEN 0.3 -> 
        //LIJN VAN 0.0 -> 0.3
        //LIJN VAN 0.7 - 1.0
        double goalXbottomR = bottomXr - (0.3 * lengte);
        double goalXbottomL = bottomXl + (0.3 * lengte); 
        double goalXleftL = bottomXl + ((lengte / 2) * 0.7);
        double goalXleftR = bottomXl + ((lengte / 2) * 0.3);
        double goalXrightL = bottomXl + ((lengte / 2) * 1.7);
        double goalXrightR = bottomXl + ((lengte/ 2) * 1.3);
        double goalYbottomL = bottomYl;
        double goalYbottomR = bottomYr;
        double goalYleftL = bottomYl - (hoogte * 0.7);
        double goalYleftR = bottomYl - (hoogte * 0.3);
        double goalYrightL = bottomYl - (hoogte * 0.3);
        double goalYrightR = bottomYl - (hoogte * 0.7);
        
        //LIJNEN TOEVOEGEN AAN LINESIDE ARRAY
        this.sides[0] = new LineSide(topX,topY,goalXleftL,goalYleftL);
        this.sides[1] = new LineSide(topX,topY,goalXrightR,goalYrightR);
        this.sides[2] = new LineSide(bottomXl,bottomYl,goalXleftR, goalYleftR);
        this.sides[3] = new LineSide(bottomXl,bottomYl,goalXbottomL,goalYbottomL);
        this.sides[4] = new LineSide(bottomXr,bottomYr,goalXrightL,goalYrightL);
        this.sides[5] = new LineSide(bottomXr,bottomYr,goalXbottomR,goalYbottomR);
        
        //GOALS TOEVOEGEN AAN LINEGOALARRAY <- HIER WORDEN SPELERS MEEGEGEVEN
        this.goals[0] = new LineGoal(goalXbottomR,goalYbottomR,goalXbottomL,goalYbottomL,spelers[0]);
        this.goals[1] = new LineGoal(goalXleftR,goalYleftR,goalXleftL,goalYleftL,spelers[1]);
        this.goals[2] = new LineGoal(goalXrightR,goalYrightR,goalXrightL,goalYrightL,spelers[2]);
    }
    /**
     * het maken van de batjes
     * @param spelers  een array van spelers
     */
    public void generateBatjes(Speler[] spelers)
    {   
        
        double straal = 0.04 * lengte;
        double snelheid = 0.0075;
        double distanceMoved = 0;
        double maxDistance = (0.4 * lengte) / 2;
        
        double xPosBatje = 0.5;
        double hoogteBatje = ((lengte/2) * tan(Math.PI/3));
        double yPosBatje = (((1 - hoogteBatje) / 2) - (0.5 - (((0.5 - (hoogteBatje / 2)) + ((lengte/2) * tan((Math.PI/6)))))) - (straal)) + hoogteBatje;
        double richtingR = 360;
        
        //BATJE SPELER 1
        this.batjes[0] = new Batje(straal,xPosBatje,yPosBatje,snelheid,richtingR,maxDistance,distanceMoved,spelers[0],Color.RED);
        spelers[0].setBatje(this.batjes[0]);
        
        xPosBatje = 0.5 - (lengte / 4) + ((straal) * sin(Math.PI / 3));
        hoogteBatje = ((lengte / 2) * tan(Math.PI / 3));
        yPosBatje = (((1 - hoogteBatje) / 2) - (0.5 - (((0.5 - (hoogteBatje / 2)) + ((lengte / 2) * tan((Math.PI / 6)))))) - (0.5 * hoogteBatje) + ((straal) * sin(Math.PI / 6))) + hoogteBatje;
        richtingR = 90 + (90 / 3);
        
        //BATJE SPELER 2
        this.batjes[1] = new Batje(straal,xPosBatje,yPosBatje,snelheid,richtingR,maxDistance,distanceMoved,spelers[1],Color.BLUE);
        spelers[1].setBatje(this.batjes[1]);

        xPosBatje = 0.5 + (lengte / 4) - ((straal) * sin(Math.PI / 3));
        hoogteBatje = ((lengte / 2) * tan(Math.PI / 3));
        yPosBatje = (((1 - hoogteBatje) / 2) - (0.5 - (((0.5 - (hoogteBatje / 2)) + ((lengte / 2) * tan((Math.PI / 6)))))) - (0.5 * hoogteBatje) + ((straal) * sin(Math.PI / 6))) + hoogteBatje;
        richtingR = 180 + ((90 / 3) * 2);
        
        //BATJE SPELER 3
        this.batjes[2] = new Batje(straal,xPosBatje,yPosBatje,snelheid,richtingR,maxDistance,distanceMoved,spelers[2],Color.GREEN);
        spelers[2].setBatje(this.batjes[2]);
    }
    
    //GENERATE BAL
    private void generateBal()
    {
        double xPos = 0.5;
        double hoogte = ((lengte/2) * tan(Math.PI/3));
        double yPos = ((1 - hoogte)/2) + (hoogte/2);
        double snelheid = 0.01;
        double richting = Math.random() * 360;
        double straal = 0.02 * lengte;
        
        this.bal = new Bal(straal,xPos,yPos,snelheid,richting);
    }
    
    //CHECK ALLE COLLISIONS <- MET EEN TIMER VOOR ELKE RONDE
    public void update()
    {
        if(waiting)
        {
            this.waitTime = waitTime - 40;
            if(this.waitTime <= 0)
            {
                //NA GOAL <- WAITING TRUE, ALS WAITING TRUE IS
                //HAALT HIJ 30ms VAN TOTALTIME AF
                //ALS WAITTIME < 0 WAITING FALSE
                this.waiting = false;
            }
        }
        else
        {
            this.bal.move();

            this.checkCollisionBatjes();
            this.checkColisionGoals();
            this.checkColisionSides();
        }
    }
    
    //CHECKCOLLISIONBATJES
    public void checkCollisionBatjes()
    {
        for(Batje b : batjes)
        {
            this.checkCollisionBatje(b);
        }
    }
    
    //CHECKCOLLISIONBATJE
    public void checkCollisionBatje(Batje b)
    {
        double hoek = 0;
        double oldHoek = 0;
        double balX = this.bal.getXPosition();
        double balY = this.bal.getYPosition();
        double balSize = this.bal.getRadius();
        double batjeX = b.getXPos();
        double batjeY = b.getYPos();
        double batSize = b.getDiameter();

        if (batjeX >= balX && batjeY >= balY) {
            if (Math.sqrt(Math.pow(batjeX - balX, 2) + Math.pow(batjeY - balY, 2)) < batSize + balSize) {
                hoek = 270 - Math.toDegrees(Math.atan2(batjeX - balX, batjeY - balY));
                balX = (batjeX - (Math.cos(Math.toRadians(hoek - 180)) * (batSize + balSize)));
                balY = (batjeY - (Math.sin(Math.toRadians(hoek - 180)) * (batSize + balSize)));

            }
        } else if (batjeX >= balX && balY >= batjeY) {
            if (Math.sqrt(Math.pow(batjeX - balX, 2) + Math.pow(balY - batjeY, 2)) < batSize + balSize) {
                hoek = 90 + Math.toDegrees(Math.atan2(batjeX - balX, balY - batjeY));
                balX = (batjeX - (Math.sin(Math.toRadians(hoek - 90)) * (batSize + balSize)));
                balY = ((Math.cos(Math.toRadians(hoek - 90)) * (batSize + balSize)) + batjeY);
            }
        } else if (balX >= batjeX && balY >= batjeY) { //(batjeX >= batjeX && balY >= batjeY) 
            if (Math.sqrt(Math.pow(balX - batjeX, 2) + Math.pow(balY - batjeY, 2)) < batSize + balSize) {
                hoek = 90 - Math.toDegrees(Math.atan2(balX - batjeX, balY - batjeY));
                balX = ((Math.cos(Math.toRadians(hoek)) * (batSize + balSize)) + batjeX);
                balY = ((Math.sin(Math.toRadians(hoek)) * (batSize + balSize)) + batjeY);

            }
        } else if (balX >= batjeX && batjeY >= balY) {
            if (Math.sqrt(Math.pow(balX - batjeX, 2) + Math.pow(batjeY - balY, 2)) < batSize + balSize) {
                hoek = 270 + Math.toDegrees(Math.atan2(balX - batjeX, batjeY - balY));
                balX = ((Math.sin(Math.toRadians(hoek - 270)) * (batSize + balSize)) + batjeX);
                balY = (batjeY - (Math.cos(Math.toRadians(hoek - 270)) * (batSize + balSize)));

            }
        }
        if (oldHoek != hoek) {
            if (bal.getLastBatje() != b)
            {
                double newDirection = hoek - (bal.getDirection() + 180) + hoek;
                while (newDirection > 360) {
                    newDirection -= 360;
                }
                while (newDirection < 0) {
                    newDirection += 360;
                }

                bal.setDirection(newDirection);
                bal.setLastBatje(b);
                bal.setLastHit(b.getSpeler());
            }
        }
        else
        {
            if (bal.getLastBatje() == b)
            {
                bal.setLastBatje(null);
            }
        }
        
    }
    /**
     * checkt collision met de zijkanten
     */
    public void checkColisionSides()
    {
        for(LineSide s : this.sides)
        {
            this.checkColisionSide(s);
        }
    }
     /**
      * kijkt of de zijkanten geraakt worden
      * @param s de lijn
      */
    private void checkColisionSide(LineSide s) {
        boolean hit = true;
        
        double x0 = bal.getXPosition();
	double y0 = bal.getYPosition();
	double x1 = s.getX1Position();
	double y1 = s.getY1Position();
	double x2 = s.getX2Position();
	double y2 = s.getY2Position();
	double n = Math.abs((x2-x1)*(y1-y0)-(x1-x0)*(y2-y1));
	double d = Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
	double dist = n/d;
	if(dist > (bal.getRadius()))
        {
            hit = false;
        }
	double d1 = Math.sqrt((x0-x1)*(x0-x1)+(y0-y1)*(y0-y1));
	if((d1-(bal.getRadius())) > d)
        {
            hit = false;
        }
	double d2 = Math.sqrt((x0-x2)*(x0-x2)+(y0-y2)*(y0-y2));
	if((d2-(bal.getRadius())) > d)
        {
            hit = false;
        }
        
        if (hit)
        {
            double hoek = Math.toDegrees(Math.atan2(s.getY2Position() - s.getY1Position(), s.getX2Position() - s.getX1Position()));
            double nieweRichting = hoek - bal.getDirection() + hoek;
            while (nieweRichting > 360) {
                nieweRichting -= 360;
            }
            while (nieweRichting < 0) {
                nieweRichting += 360;
            }
            bal.setDirection(nieweRichting);
        }
        
    }
    /**
     * checken voor collisions met de goals
     */
    public void checkColisionGoals()
   
    {
        for(LineGoal g : this.goals)
        {
            this.checkColisionGoal(g);
        }
    }
    /**
     * kijken of de bal de goallijn heeft geraakt.
     * @param g  de goalline
     */
    private void checkColisionGoal(LineGoal g) 
    {
        boolean hit = true;
        
        double x0 = bal.getXPosition();
	double y0 = bal.getYPosition();
	double x1 = g.getX1Position();
	double y1 = g.getY1Position();
	double x2 = g.getX2Position();
	double y2 = g.getY2Position();
	double n = Math.abs((x2-x1)*(y1-y0)-(x1-x0)*(y2-y1));
	double d = Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
	double dist = n/d;
	if(dist > (bal.getRadius()))
        {
            hit = false;
        }
	double d1 = Math.sqrt((x0-x1)*(x0-x1)+(y0-y1)*(y0-y1));
	if((d1-(bal.getRadius())) > d)
        {
            hit = false;
        }
	double d2 = Math.sqrt((x0-x2)*(x0-x2)+(y0-y2)*(y0-y2));
	if((d2-(bal.getRadius())) > d)
        {
            hit = false;
        }
        
        if (hit)
        {
            
            //bal.reset();
           
            if(bal.getLastHit() != null)
            {
                bal.getLastHit().scorePlus();
                g.getSpeler().scoreMin();
                if(bal.getLastHit() != g.getSpeler())
                {
                    round++;
                }
                
            }
            
            bal.reset();
            this.setWait(3);
        }
    }
    
    public int getRound()
    {
        return this.round;
    }
    /**
     * het wachten als er is gescoort dat de bal even in het midden wacht
     * @param sec duur van het wachten
     */
    public void setWait(int sec)
    {
        this.waiting = true;
        this.waitTime = sec * (1000);
    }
    /**
     * haalt de zijkanten op
     * @return een array met de zijkanten
     */
    public LineSide[] getLineSides()
    {
        return this.sides;
    }
    /**
     * haalt de goals op
     * @return een array met de goals
     */
    public LineGoal[] getLineGoals()
    {
        return this.goals;
    }
}
